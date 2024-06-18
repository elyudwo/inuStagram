package io.kr.inu.infra.redis.user;

import io.kr.inu.infra.redis.common.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLikeServiceImpl {

    private final RedissonClient redissonClient;

    @DistributedLock(hashKey = "#hashKey", field = "#field")
    public int plusLike(String hashKey, String field, int a) {
        int likeCnt = currentLike(hashKey, field);
        setLike(hashKey, field, likeCnt + 1);
        return 3;
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
