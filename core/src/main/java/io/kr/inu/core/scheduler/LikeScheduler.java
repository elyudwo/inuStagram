package io.kr.inu.core.scheduler;

import io.kr.inu.core.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class LikeScheduler {

    private final RedisTemplate<Long, Long> redisTemplate;
    private final LikeRepository likeRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void reflectLikeFromRedis() {
        Set<Long> keys = redisTemplate.keys(Long.valueOf("*"));

        for(Long key : keys) {
            Long likeCnt = redisTemplate.opsForValue().get(key);
            // TODO: like를 RDB에 반영해야 하는데 ERD 수정해야할듯
        }
    }

    private void plusLike() {

    }
}
