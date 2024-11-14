package io.kr.inu.core.common.ratelimiter;

import io.kr.inu.infra.redis.common.CustomSpringELParser;
import io.kr.inu.infra.redis.common.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimiterAspect {
    private final RateLimiterService rateLimiterService;
    private final View error;

    @Around("@annotation(io.kr.inu.core.common.ratelimiter.RateLimiter)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        String key = getDynamicValue(signature, joinPoint, rateLimiter.key());
        int duration = rateLimiter.duration();

        if (!rateLimiterService.isAllowed(key, duration)) {
            log.info("rate limiter alert! : {}", key);
            throw new IllegalArgumentException("rate limiter alert!");
        }

        return joinPoint.proceed();
    }

    public String getDynamicValue(MethodSignature signature, ProceedingJoinPoint joinPoint, String distributedLock) {
        return (String) CustomSpringELParser.getDynamicValue(
                signature.getParameterNames(),
                joinPoint.getArgs(),
                distributedLock);
    }
}
