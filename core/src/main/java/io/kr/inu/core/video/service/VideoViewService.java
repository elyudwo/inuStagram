package io.kr.inu.core.video.service;

import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.domain.VideoViewEntity;
import io.kr.inu.core.video.dto.OneDayViewsData;
import io.kr.inu.core.video.repository.VideoViewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VideoViewService {

    private final RedisTemplate<String, Long> stringLongRedisTemplate;
    private final VideoViewRepository videoViewRepository;

    public void increaseVideoView(Long videoId) {
        String key = parsingRedisKey(videoId);

        long startTime = System.nanoTime();
        for (int i = 0; i < 10_000; i++) {
            stringLongRedisTemplate.opsForValue().increment(key, 1);
        }

        long endTime = System.nanoTime();
        long durationInMillis = (endTime - startTime) / 1_000_000;

        log.info("Execution time: " + durationInMillis + " ms");
    }

    private String parsingRedisKey(Long videoId) {
        LocalDate now = LocalDate.now();
        String tmp = videoId.toString();
        return tmp + ":" + now;
    }

    public Long getVideoView(Long videoId) {
        Long beforeViewCount = getBeforeViewCount(videoId);
        Long viewCount = stringLongRedisTemplate.opsForValue().get(parsingRedisKey(videoId));
        if (viewCount == null) {
            stringLongRedisTemplate.opsForValue().set(parsingRedisKey(videoId) + ":today", 0L);
            return beforeViewCount;
        }

        return beforeViewCount + viewCount;
    }

    private Long getBeforeViewCount(Long videoId) {
        Long beforeViewCount = stringLongRedisTemplate.opsForValue().get(videoId.toString());
        if (beforeViewCount == null) {
            VideoViewEntity videoViewEntity = videoViewRepository.findByVideoId(videoId).orElseThrow(
                    () -> new EntityNotFoundException("존재하지 않는 영상 Id 입니다."));
            String todayKey = videoId.toString() + ":today";
            stringLongRedisTemplate.opsForValue().set(todayKey, videoViewEntity.getViewCount());
            beforeViewCount = videoViewEntity.getViewCount();
        }
        return beforeViewCount;
    }

    public void createVideoView(VideoEntity videoEntity) {
        VideoViewEntity videoViewEntity = VideoViewEntity.builder()
                .viewCount(0L)
                .video(videoEntity)
                .build();

        videoViewRepository.save(videoViewEntity);
    }

    public List<OneDayViewsData> getOneDayViewsDataList() {
        Set<String> keys = getYesterdayViewKeys();
        log.info("getOneDayKey : " + keys.size());

        return keys.stream()
                .map(key -> {
                    Long viewCount = stringLongRedisTemplate.opsForValue().get(key);
                    Long videoViewId = parsingVideoViewIdFromKey(key);

                    return OneDayViewsData.builder()
                            .viewTime(LocalDate.now().minusDays(1))
                            .viewCount(viewCount)
                            .videoViewId(videoViewId)
                            .build();
                })
                .toList();
    }

    public void deleteViewsDateFromRedis() {
        Set<String> keys = getYesterdayViewKeys();
        Set<String> allDayViewKeys = getAllDayViewKeys();

        if (keys != null && !keys.isEmpty()) {
            stringLongRedisTemplate.delete(keys);
        }
        if (allDayViewKeys != null && !allDayViewKeys.isEmpty()) {
            stringLongRedisTemplate.delete(allDayViewKeys);
        }
    }

    private Set<String> getAllDayViewKeys() {
        String pattern = "*:today";
        return stringLongRedisTemplate.keys(pattern);
    }

    private Set<String> getYesterdayViewKeys() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        String pattern = "*:" + today;
        return stringLongRedisTemplate.keys(pattern);
    }

    private Long parsingVideoViewIdFromKey(String key) {
        String videoViewIdStr = key.split(":")[0];
        return Long.parseLong(videoViewIdStr);
    }

}
