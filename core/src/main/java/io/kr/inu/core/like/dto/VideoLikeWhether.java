package io.kr.inu.core.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoLikeWhether {

    private final boolean like;

    @Builder
    public VideoLikeWhether(boolean like) {
        this.like = like;
    }
}
