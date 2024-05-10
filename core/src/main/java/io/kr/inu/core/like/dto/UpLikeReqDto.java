package io.kr.inu.core.like.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpLikeReqDto {

    private Long videoId;

    @Builder
    public UpLikeReqDto(Long videoId) {
        this.videoId = videoId;
    }
}
