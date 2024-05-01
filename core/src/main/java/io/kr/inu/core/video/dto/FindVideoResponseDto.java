package io.kr.inu.core.video.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class FindVideoResponseDto {

    private final List<EachVideoData> allVideos;
    private final boolean next;

    @Builder
    public FindVideoResponseDto(List<EachVideoData> allVideos, boolean next) {
        this.allVideos = allVideos;
        this.next = next;
    }
}
