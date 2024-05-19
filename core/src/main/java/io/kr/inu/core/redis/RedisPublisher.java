package io.kr.inu.core.redis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> template;

    public void publish(ChannelTopic topic, HarmfulVideoReqDto dto) {
        template.convertAndSend(topic.getTopic(), dto);
        log.info("메시지 발행 성공");
    }

}
