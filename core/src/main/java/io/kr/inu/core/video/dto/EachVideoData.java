package io.kr.inu.core.video.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EachVideoData {

    private final Long videoId;
    private final String videoUrl;
    private final String thumbnailUrl;
    private final String title;

    @Builder
    public EachVideoData(Long videoId, String videoUrl, String thumbnailUrl, String title) {
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
    }
}