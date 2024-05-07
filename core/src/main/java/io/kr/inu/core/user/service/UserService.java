package io.kr.inu.core.user.service;

import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(String email) {
        userRepository.save(UserEntity.builder()
                .email(email)
                .build());
    }

}
