package io.kr.inu.core.video.service;

import io.kr.inu.core.like.dto.EachVideoLikes;
import io.kr.inu.core.like.repository.LikeRepository;
import io.kr.inu.core.like.service.LikeService;
import io.kr.inu.core.user.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoLikeService {

    private final RedisTemplate<Long, Long> redisTemplate;
    private final LikeService likeService;
    private final UserValidateService userValidateService;
    private final LikeRepository likeRepository;

    public EachVideoLikes getVideoLike(String email, Long videoId) {
        userValidateService.existUserByEmail(email);
        Long likeCnt = redisTemplate.opsForValue().get(videoId);
        if (likeCnt == null) {
            likeCnt = likeService.getVideoLike(email, videoId).getLike();
            redisTemplate.opsForValue().set(videoId, likeCnt);
        }

        return EachVideoLikes.builder()
                .like(likeCnt)
                .build();
    }


    public void plusLike(Long videoId, String email) {
        userValidateService.existUserByEmail(email);
        Long likeCnt = redisTemplate.opsForValue().get(videoId);
        if (likeCnt == null) {
            getVideoLikeAndPlusLike(videoId);
            return;
        }
        redisTemplate.opsForValue().set(videoId, likeCnt + 1);
    }

    private void getVideoLikeAndPlusLike(Long videoId) {
        // TODO: 이미 좋아요를 누른 유저인지 검증

        Long likeCnt = likeRepository.countByVideo_Id(videoId);
        redisTemplate.opsForValue().set(videoId, likeCnt + 1);
    }

}
