package io.kr.inu.core.user.service;

import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidateService {

    private final UserRepository userRepository;

    public void existUserByEmail(String email) {
        if(!userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
    }

    public Long validateUserByEmailAndReturnId(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        return user.getId();
    }

    public boolean duplicateCheckUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
