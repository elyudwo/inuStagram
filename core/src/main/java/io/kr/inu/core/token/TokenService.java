package io.kr.inu.core.token;

import io.kr.inu.core.email.dto.CertificationResponse;
import io.kr.inu.core.email.dto.MailInfo;
import io.kr.inu.core.token.dto.TokenResponse;
import io.kr.inu.core.user.service.UserService;
import io.kr.inu.infra.jwt.JwtProvider;
import io.kr.inu.infra.jwt.dto.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public TokenResponse issueToken(MailInfo mailInfo) {
        JwtDto jwtDto = jwtProvider.createJwtResponseDto(mailInfo.getEmail());
        userService.registerUser(mailInfo.getEmail());
        return new TokenResponse(jwtDto.getAccessToken());
    }
}
