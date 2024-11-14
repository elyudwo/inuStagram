package io.kr.inu.core.video.scheduler;

import io.kr.inu.core.video.domain.VideoViewDetailEntity;
import io.kr.inu.core.video.dto.OneDayViewsData;
import io.kr.inu.core.video.repository.VideoViewDetailRepository;
import io.kr.inu.core.video.service.VideoViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class VideoViewCountScheduler {

    private final VideoViewService videoViewService;
    private final VideoViewDetailRepository videoViewDetailRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void executeDailyTask() {
        log.info("Daily task started at 00:00");
        performTask();
        log.info("Daily task completed");
    }

    private void performTask() {
        log.info("Performing scheduled task...");
        List<OneDayViewsData> oneDayViewsData = videoViewService.getOneDayViewsDataList();

        List<VideoViewDetailEntity> videoViewDetails = oneDayViewsData.stream()
                .map(data -> VideoViewDetailEntity.builder()
                        .viewTime(data.getViewTime())
                        .viewCount(data.getViewCount())
                        .videoViewId(data.getVideoViewId())
                        .build())
                .collect(Collectors.toList());

        log.info("key size: {}", videoViewDetails.size());

        videoViewDetailRepository.saveAll(videoViewDetails);
        videoViewService.deleteViewsDateFromRedis();
    }


}