package io.kr.inu.webclient.api.email;

import io.kr.inu.core.email.EmailSendService;
import io.kr.inu.core.email.dto.CertificationResponse;
import io.kr.inu.webclient.api.email.dto.CertificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
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
public class EmailController {

    private final EmailSendService emailSendService;

    @Operation(summary = "이메일 인증코드 전송", description = "바디에 {email} json 형식으로 보내주시면 됩니다. ")
    @PostMapping("/v1/email")
    public ResponseEntity<CertificationResponse> sendEmail(@Valid @RequestBody CertificationRequest request) throws MessagingException {
        return ResponseEntity.ok(emailSendService.sendEmail(request.toMailInfo()));
    }

}