package io.kr.inu.core.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindUserRespDto {

    private final String email;
    private final String color;

    @Builder
    public FindUserRespDto(String email, String color) {
        this.email = email;
        this.color = color;
    }
}
