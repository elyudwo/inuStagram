package io.kr.inu.core.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequestDto {

    private Long userId;
    private Long videoId;
}
