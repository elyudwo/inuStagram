package io.kr.inu.webclient.api.token;

import io.kr.inu.core.token.TokenService;
import io.kr.inu.core.token.dto.TokenResponse;
import io.kr.inu.webclient.api.email.dto.CertificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

        private final TokenService tokenService;

        @Operation(summary = "토큰 발급 API", description = "바디에 {email} json 형식으로 보내주시면 됩니다.")
        @PostMapping("/v1/token/issue")
        public ResponseEntity<TokenResponse> sendEmail(@Valid @RequestBody CertificationRequest request) {
            return ResponseEntity.ok(tokenService.issueToken(request.toMailInfo()));
    }
}
