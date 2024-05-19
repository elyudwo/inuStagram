package io.kr.inu.core.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HarmfulVideoReqDto {

    private Long videoId;
    private String fileName;

    public HarmfulVideoReqDto(Long videoId, String fileName) {
        this.videoId = videoId;
        this.fileName = fileName;
    }
}
