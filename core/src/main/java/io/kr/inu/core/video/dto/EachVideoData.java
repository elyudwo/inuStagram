package io.kr.inu.core.video.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EachVideoData {

    private final String url;
    private final String title;
    private final Long like;

    @Builder
    public EachVideoData(String url, String title, Long like) {
        this.url = url;
        this.title = title;
        this.like = like;
    }
}
