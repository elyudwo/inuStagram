package io.kr.inu.webclient.api.redis;

import io.kr.inu.core.redis.RedisPubService;
import io.kr.inu.core.redis.LikeRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/redis/pubsub")
public class RedisPubSubController {

    private final RedisPubService redisSubscribeService;

    @PostMapping("/send")
    public void sendMessage(@RequestParam String channel, @RequestBody LikeRequestDto message) {
        redisSubscribeService.pubMsgChannel(channel, message);
    }

    @PostMapping("/cancle")
    public void cancelSubChannel(@RequestParam String channel) {
        redisSubscribeService.cancelSubChannel(channel);
    }
}
