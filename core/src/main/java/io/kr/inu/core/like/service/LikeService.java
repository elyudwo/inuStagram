package io.kr.inu.core.like.service;

import io.kr.inu.core.like.domain.LikeEntity;
import io.kr.inu.core.like.dto.EachVideoLikes;
import io.kr.inu.core.like.dto.UpLikeReqDto;
import io.kr.inu.core.like.dto.VideoLikeWhether;
import io.kr.inu.core.like.repository.LikeRepository;
import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.user.repository.UserRepository;
import io.kr.inu.core.user.service.UserValidateService;
import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.dto.EachVideoData;
import io.kr.inu.core.video.dto.FindVideoResponseDto;
import io.kr.inu.core.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final UserValidateService userValidateService;


    public EachVideoLikes getVideoLike(String email, Long videoId) {
        userValidateService.existUserByEmail(email);
        Long likes = likeRepository.countByVideo_Id(videoId);

        return EachVideoLikes.builder()
                .like(likes)
                .build();
    }

    public void plusLike(UpLikeReqDto reqDto, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 이메일을 가진 사용자를 찾을 수 없습니다 토큰을 다시 확인해주세요: " + email));
        VideoEntity video = videoRepository.findById(reqDto.getVideoId()).orElseThrow(() -> new IllegalArgumentException("해당 식별자를 가진 비디오를 찾을 수 업습니다"));
        if (likeRepository.existsByVideoAndUser(video, user)) {
            throw new IllegalArgumentException("한 동영상에 한개의 좋아요만 가능해요.");
        }

        LikeEntity like = LikeEntity.builder()
                .user(user)
                .video(video)
                .build();

        likeRepository.save(like);
    }

    public void deleteLike(UpLikeReqDto reqDto, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        VideoEntity video = videoRepository.findById(reqDto.getVideoId()).orElseThrow(RuntimeException::new);
        LikeEntity like = likeRepository.findByVideoAndUser(video, user).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영상입니다"));
        likeRepository.delete(like);
    }

    public FindVideoResponseDto findVideoByUserLikes(String email, int page, int size) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        Pageable pageable = PageRequest.of(page, size);

        List<EachVideoData> posts = likeRepository.findVideoByUserLikes(user, pageable);
        boolean next = isNext(likeRepository.countByUser_Id(user.getId()), page, size);
        ;

        return FindVideoResponseDto.builder()
                .allVideos(posts)
                .next(next)
                .build();
    }

    private boolean isNext(long count, int page, int size) {
        return (long) size * page + size < count;
    }

    public VideoLikeWhether checkVideoLikeByUser(String email, Long videoId) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + email));
        VideoEntity video = videoRepository.findById(videoId).orElseThrow(() -> new IllegalArgumentException("해당 식별자를 가진 비디오를 찾을 수 업습니다"));
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
