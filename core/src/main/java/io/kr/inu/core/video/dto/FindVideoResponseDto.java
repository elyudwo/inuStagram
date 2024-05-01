package io.kr.inu.core.video.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class FindVideoResponseDto {

    Slice<EachVideoData> allVideos;

    @Builder
    public FindVideoResponseDto(Slice<EachVideoData> allVideos) {
        this.allVideos = allVideos;
    }
}
