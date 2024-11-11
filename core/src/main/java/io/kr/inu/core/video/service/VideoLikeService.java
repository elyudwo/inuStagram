package io.kr.inu.core.video.service;

import io.kr.inu.core.like.domain.LikeEntity;
import io.kr.inu.core.like.dto.EachVideoLikes;
import io.kr.inu.core.like.dto.UpLikeReqDto;
import io.kr.inu.core.like.dto.VideoLikeWhether;
import io.kr.inu.core.like.repository.LikeRepository;
import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.user.service.UserService;
import io.kr.inu.core.user.service.UserValidateService;
import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.dto.EachVideoData;
import io.kr.inu.core.video.dto.FindVideoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class VideoLikeService {

    private final RedisTemplate<Long, Long> redisTemplate;
    private final UserValidateService userValidateService;
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final VideoService videoService;

    public EachVideoLikes getVideoLikeByCache(String email, Long videoId) {
        userValidateService.existUserByEmail(email);
        Long likeCnt = redisTemplate.opsForValue().get(videoId);
        if (likeCnt == null) {
            likeCnt = getVideoLike(email, videoId).getLike();
            redisTemplate.opsForValue().set(videoId, likeCnt);
        }

        return EachVideoLikes.builder()
                .like(likeCnt)
                .build();
    }

    public EachVideoLikes getVideoLike(String email, Long videoId) {
        userValidateService.existUserByEmail(email);
        Long likes = likeRepository.countByVideo_Id(videoId);

        return EachVideoLikes.builder()
                .like(likes)
                .build();
    }

    public void plusLike(UpLikeReqDto reqDto, String email) {
        UserEntity user = userService.getUserByEmail(email);
        VideoEntity video = videoService.getVideoById(reqDto.getVideoId());
        if (likeRepository.existsByVideoAndUser(video, user)) {
            throw new IllegalArgumentException("한 동영상에 한개의 좋아요만 가능해요.");
        }

        LikeEntity like = LikeEntity.builder()
                .user(user)
                .video(video)
                .build();

        likeRepository.save(like);

        redisTemplate.opsForValue().set(reqDto.getVideoId(), likeRepository.countByVideo_Id(video.getId()));
    }

    public void deleteLike(UpLikeReqDto reqDto, String email) {
        UserEntity user = userService.getUserByEmail(email);
        VideoEntity video = videoService.getVideoById(reqDto.getVideoId());
        LikeEntity like = likeRepository.findByVideoAndUser(video, user).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영상입니다"));
        likeRepository.delete(like);
        redisTemplate.opsForValue().set(reqDto.getVideoId(), likeRepository.countByVideo_Id(video.getId()));
    }

    public FindVideoResponseDto findVideoByUserLikes(String email, int page, int size) {
        UserEntity user = userService.getUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);

        List<EachVideoData> posts = likeRepository.findVideoByUserLikes(user, pageable);
        boolean next = isNext(likeRepository.countByUser_Id(user.getId()), page, size);

        return FindVideoResponseDto.builder()
                .allVideos(posts)
                .next(next)
                .build();
    }

    private boolean isNext(long count, int page, int size) {
        return (long) size * page + size < count;
    }

    public VideoLikeWhether checkVideoLikeByUser(String email, Long videoId) {
        UserEntity user = userService.getUserByEmail(email);
        VideoEntity video = videoService.getVideoById(videoId);
        if (likeRepository.existsByVideoAndUser(video, user)) {
            return VideoLikeWhether.builder()
                    .like(true)
                    .build();
        }
        return VideoLikeWhether.builder()
                .like(false)
                .build();
    }
}
