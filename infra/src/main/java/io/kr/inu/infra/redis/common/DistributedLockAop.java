package io.kr.inu.infra.redis.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DistributedLockAop {

    private final RedissonClient redissonClient;

    @Around("@annotation(io.kr.inu.infra.redis.common.DistributedLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        String hashKey = getDynamicValue(signature, joinPoint, distributedLock.hashKey());
        String field = getDynamicValue(signature, joinPoint, distributedLock.field());

        RMap<String, Integer> map = redissonClient.getMap(hashKey);
        RLock lock = map.getLock(field);

        Object result;

        try {
            boolean available = lock.tryLock(1, 3, TimeUnit.SECONDS);
            if (!available) {
                log.warn("Redisson GetLock Timeout {}", field);
                throw new IllegalArgumentException("Could not acquire lock for field: " + field);
            }

             result = joinPoint.proceed();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already Unlocked {}", field);
            }
        }
        return result;
    }

    public String getDynamicValue(MethodSignature signature, ProceedingJoinPoint joinPoint, String distributedLock) {
        return (String) CustomSpringELParser.getDynamicValue(
                signature.getParameterNames(),
                joinPoint.getArgs(),
                distributedLock);
    }
}
