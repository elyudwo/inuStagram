package io.kr.inu.core.redis;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class RedisListenerRegistrar {

    @Autowired
    private RedisMessageListenerContainer container;

    @Autowired
    private MessageListener messageListener;

    @Value("${channel.name}")
    private String channelName;

    @PostConstruct
    public void registerListener() {
        container.addMessageListener(messageListener, new ChannelTopic(channelName));
    }
}
