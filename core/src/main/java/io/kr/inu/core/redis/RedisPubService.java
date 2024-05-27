package io.kr.inu.core.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisPubService {

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisPublisher redisPublisher;
    private final RedisSubscribeListener redisSubscribeListener;
    private final ChannelTopic channelTopic = new ChannelTopic("flaskServer");

    public void pubMsgChannel(HarmfulVideoReqDto dto) {
        log.info("메시지 발행 : " + dto.getFileName());
        redisPublisher.publish(channelTopic, dto);
    }

    public void cancelSubChannel(String channel) {
        redisMessageListenerContainer.removeMessageListener(redisSubscribeListener);
    }
}
