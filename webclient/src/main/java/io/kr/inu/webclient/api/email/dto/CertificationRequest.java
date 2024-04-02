package io.kr.inu.webclient.api.email.dto;

import io.kr.inu.core.email.dto.MailInfo;
import io.kr.inu.webclient.api.email.validator.NormalEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CertificationRequest {

    @Schema(description = "이메일 주소", example = "dudqk9696@naver.com")
    @NotBlank
    @NormalEmail
    private String email;

    public MailInfo toMailInfo() {
        return MailInfo.builder()
                .email(email)
                .build();
    }

}