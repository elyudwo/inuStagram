package io.kr.inu.infra.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtDto {

    private final String accessToken;

    @Builder
    public JwtDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
