package io.kr.inu.core.redis;

import io.kr.inu.infra.redis.LikeDto;
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

    public void pubMsgChannel(String channel, LikeRequestDto message) {
        redisMessageListenerContainer.addMessageListener(redisSubscribeListener, new ChannelTopic(channel));
        LikeDto likeDto = LikeDto.builder()
                        .userId(message.getUserId())
                        .videoId(message.getVideoId())
                        .build();

        redisPublisher.publish(new ChannelTopic(channel), likeDto);
    }

    public void cancelSubChannel(String channel) {
        redisMessageListenerContainer.removeMessageListener(redisSubscribeListener);
    }
}
