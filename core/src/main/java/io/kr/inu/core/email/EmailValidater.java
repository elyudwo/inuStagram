package io.kr.inu.core.email;

import io.kr.inu.core.email.dto.ValidMailInfo;
import io.kr.inu.infra.redis.EmailRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailValidater {

    private final EmailRepository emailRepository;

    public boolean validEmail(ValidMailInfo info) {
        String cachedAuthCode = emailRepository.validEmail(info.getEmail());
        return cachedAuthCode != null && cachedAuthCode.equals(info.getAuthCode());
    }
}
