package io.kr.inu.infra.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeDto {

    private Long userId;
    private Long videoId;

    @Builder
    public LikeDto(Long userId, Long videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }
}
