package io.kr.inu.core.common.ratelimiter;

import io.kr.inu.infra.redis.common.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimiterAspect {
    private final RateLimiterService rateLimiterService;

    @Around("@annotation(io.kr.inu.core.common.ratelimiter.RateLimiter)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        String key = getDynamicValue(signature, joinPoint, rateLimiter.key());
        int duration = rateLimiter.duration();

        if (!rateLimiterService.isAllowed(key, duration)) {
            log.info("rate limiter alert! : {}", key);
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limiter alert!");
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
