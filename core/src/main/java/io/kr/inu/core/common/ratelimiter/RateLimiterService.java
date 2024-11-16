package io.kr.inu.core.common.ratelimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RateLimiterService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> rateLimiterScript;

    public RateLimiterService() {
        this.rateLimiterScript = new DefaultRedisScript<>();
        rateLimiterScript.setScriptText(
            "local key = KEYS[1]\n" +
            "local ttl = tonumber(ARGV[1])\n" +
            "if redis.call(\"EXISTS\", key) == 1 then\n" +
            "    return 0\n" +
            "else\n" +
            "    redis.call(\"SETEX\", key, ttl, \"1\")\n" +
            "    return 1\n" +
            "end"
        );
        rateLimiterScript.setResultType(Long.class);
    }

    public boolean isAllowed(String userKey, int duration) {
        Long result = redisTemplate.execute(
                rateLimiterScript,
                Collections.singletonList(userKey),
                String.valueOf(duration)
        );
        return result != null && result == 1;
    }
}
