package io.kr.inu.core.video.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OneDayViewsData {
    private final Long viewCount;
    private final LocalDate viewTime;
    private final Long videoViewId;

    @Builder
    public OneDayViewsData(Long viewCount, LocalDate viewTime, Long videoViewId) {
        this.viewCount = viewCount;
        this.viewTime = viewTime;
        this.videoViewId = videoViewId;
    }
}
