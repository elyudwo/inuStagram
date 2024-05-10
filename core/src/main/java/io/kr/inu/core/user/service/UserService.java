package io.kr.inu.core.user.service;

import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.user.dto.FindUserRespDto;
import io.kr.inu.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(String email) {
        if(!userRepository.existsByEmail(email)) {
            userRepository.save(UserEntity.builder()
                    .email(email)
                    .build());
        }
    }

    public FindUserRespDto findUser(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);

        return FindUserRespDto.builder()
                .email(user.getEmail())
                .color(user.getColor())
                .build();
    }

}
