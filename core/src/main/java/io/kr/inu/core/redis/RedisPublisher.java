package io.kr.inu.core.redis;

import io.kr.inu.infra.redis.LikeDto;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> template;

    public void publish(ChannelTopic topic, LikeDto dto) {
        template.convertAndSend(topic.getTopic(), dto);
    }

}
