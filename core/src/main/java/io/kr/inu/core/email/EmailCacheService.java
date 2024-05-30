package io.kr.inu.core.email;

import io.kr.inu.infra.redis.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailCacheService {

    private static final int AUTH_CODE_LENGTH = 5;
    private static final int KEY_SIZE = 10;
    private final EmailRepository emailRepository;

    public String getAndCacheAuthCode(String email) {
        String authCode = generateAuthCode();
        emailRepository.saveCertificationNumber(email, authCode);

        return authCode;
    }

    private String generateAuthCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
            key.append(random.nextInt(KEY_SIZE));
        }
        log.info("이메일 인증 키 값 : " + key.toString());
        return key.toString();
    }
}
