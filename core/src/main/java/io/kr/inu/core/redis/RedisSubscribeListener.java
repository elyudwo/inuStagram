package io.kr.inu.core.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisSubscribeListener implements MessageListener {

    private final RedisTemplate<String, Object> template;
    private final ObjectMapper objectMapper;
    private final VideoRepository videoRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = template.getStringSerializer().deserialize(message.getBody());
            HarmfulVideoDto dto = objectMapper.readValue(publishMessage, HarmfulVideoDto.class);

            log.info("Redis SUB Message : {}", publishMessage);

            VideoEntity video = videoRepository.findById(dto.getVideoId()).
                    orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영상입니다."));
            videoRepository.delete(video);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}