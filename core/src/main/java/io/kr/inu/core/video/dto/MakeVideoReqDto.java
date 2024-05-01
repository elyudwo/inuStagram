package io.kr.inu.core.video.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MakeVideoReqDto {

    private String title;
    private String email;

    @Builder
    public MakeVideoReqDto(String title, String email) {
        this.title = title;
        this.email = email;
    }

    public static MakeVideoReqDto of(String title, String email) {
        return MakeVideoReqDto.builder()
                .title(title)
                .email(email)
                .build();
    }
}
