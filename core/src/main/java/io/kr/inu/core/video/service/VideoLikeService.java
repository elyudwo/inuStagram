package io.kr.inu.core.video.service;

import io.kr.inu.core.like.repository.LikeRepository;
import io.kr.inu.core.like.service.LikeService;
import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoLikeService {

    private final RedisTemplate<Long, Long> redisTemplate;
    private final LikeRepository likeRepository;

    public void plusLike(Long videoId) {
        Long likeCnt = redisTemplate.opsForValue().get(videoId);
        if(likeCnt == null) {
            getVideoLikeAndPlusLike(videoId);
            return;
        }
        redisTemplate.opsForValue().set(videoId, likeCnt+1);
    }

    private void getVideoLikeAndPlusLike(Long videoId) {
        Long likeCnt = likeRepository.countByVideo_Id(videoId);
        redisTemplate.opsForValue().set(videoId, likeCnt+1);
    }

}
