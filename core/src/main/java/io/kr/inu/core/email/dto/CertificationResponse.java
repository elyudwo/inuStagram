package io.kr.inu.core.email.dto;

import io.kr.inu.infra.jwt.dto.JwtDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CertificationResponse {

    private final String certificateNumber;
    private final JwtDto token;


    @Builder
    public CertificationResponse(String certificateNumber, JwtDto token) {
        this.certificateNumber = certificateNumber;
        this.token = token;
    }
}
