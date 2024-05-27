package io.kr.inu.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final RedissonClient redissonClient;

    public void plusLike(String hashKey, String field) {
        RMap<String, Integer> map = redissonClient.getMap(hashKey);
        RLock lock = map.getLock(field);

        try {
            boolean available = lock.tryLock(1, 3, TimeUnit.SECONDS);
            if (!available) {
                log.warn("Redisson GetLock Timeout {}", field);
                throw new IllegalArgumentException();
            }

            int stock = currentLike(hashKey, field);

            setLike(hashKey, field, stock + 1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already UnLock {}", field);
            }
        }
    }

    public void setLike(String hashKey, String field, int amount){
        RMap<String, Integer> map = redissonClient.getMap(hashKey);
        map.fastPut(field, amount);
    }

    public int currentLike(String hashKey, String field){
        RMap<String, Integer> map = redissonClient.getMap(hashKey);
        return map.getOrDefault(field, 0);
    }
}
