package io.kr.inu.core.like.service;

import io.kr.inu.core.like.domain.LikeEntity;
import io.kr.inu.core.like.dto.EachVideoLikes;
import io.kr.inu.core.like.dto.UpLikeReqDto;
import io.kr.inu.core.like.repository.LikeRepository;
import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.user.repository.UserRepository;
import io.kr.inu.core.user.service.UserService;
import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.dto.EachVideoData;
import io.kr.inu.core.video.dto.FindVideoResponseDto;
import io.kr.inu.core.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public EachVideoLikes getVideoLike(Long videoId) {
        Long likes = likeRepository.countByVideo_Id(videoId);

        return EachVideoLikes.builder()
                .like(likes)
                .build();
    }

    public void plusLike(UpLikeReqDto reqDto, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        VideoEntity video = videoRepository.findById(reqDto.getVideoId()).orElseThrow(RuntimeException::new);
        if(likeRepository.existsByVideoAndUser(video, user)) {
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
        LikeEntity like = LikeEntity.builder()
                .user(user)
                .video(video)
                .build();

        likeRepository.delete(like);
    }

    public FindVideoResponseDto findVideoByUserLikes(String email, int page, int size) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        Pageable pageable = PageRequest.of(page, size);

        List<EachVideoData> posts = likeRepository.findVideoByUserLikes(user, pageable);
        boolean next = isNext(likeRepository.countByUser_Id(user.getId()), page, size);;

        return FindVideoResponseDto.builder()
                .allVideos(posts)
                .next(next)
                .build();
    }

    private boolean isNext(long count, int page, int size) {
        return (long) size * page + size < count;
    }
}
