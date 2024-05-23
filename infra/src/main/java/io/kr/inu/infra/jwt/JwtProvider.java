package io.kr.inu.infra.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.kr.inu.infra.jwt.dto.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Transactional
@Service
public class JwtProvider {

    private String SECRET = "NANA";

    public JwtDto createJwtResponseDto(String email) {
        return JwtDto.builder()
                .accessToken(createAccessToken(email))
                .build();
    }

    private String createAccessToken(String email) {
        String jwtToken = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 24 * 1000 * 100))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(SECRET));
        return "Bearer " + jwtToken;
    }

}
