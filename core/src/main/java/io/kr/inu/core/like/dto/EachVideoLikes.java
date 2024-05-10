package io.kr.inu.core.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EachVideoLikes {

    private final Long like;

    @Builder
    public EachVideoLikes(Long like) {
        this.like = like;
    }
}
