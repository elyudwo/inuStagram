package io.kr.inu.core.email.dto;

import io.kr.inu.infra.jwt.dto.JwtDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CertificationResponse {

    private final String certificateNumber;

    @Builder
    public CertificationResponse(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
}
