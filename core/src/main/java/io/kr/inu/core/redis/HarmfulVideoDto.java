package io.kr.inu.core.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HarmfulVideoDto {

    private Long videoId;

    public HarmfulVideoDto(Long videoId) {
        this.videoId = videoId;
    }
}
