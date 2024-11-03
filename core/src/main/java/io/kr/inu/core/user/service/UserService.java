package io.kr.inu.core.user.service;

import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.user.dto.FindUserRespDto;
import io.kr.inu.core.user.repository.UserRepository;
import io.kr.inu.infra.redis.user.UserLikeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserValidateService userValidateService;
    private final EntityManager entityManager;
    private final UserLikeService userLikeService;


    public void registerUser(String email) {
        if (!userValidateService.duplicateCheckUserByEmail(email)) {
            userRepository.save(UserEntity.builder()
                    .email(email)
                    .build());
        }
    }

    public FindUserRespDto findUser(String email) {
        userRepository.existsByEmail(email);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);

        return FindUserRespDto.builder()
                .email(user.getEmail())
                .color(user.getColor())
                .build();
    }

    public void plusLike(String email) {
        userLikeService.plusLike(email + ":key", email);
    }

    public Long findLike(String email) {
        return (long) userLikeService.currentLike(email + ":key", email);
    }

    public Long plusTest() {
        UserEntity user = entityManager.find(UserEntity.class, 1L, LockModeType.PESSIMISTIC_WRITE);
//        UserEntity user = userRepository.findByEmail("dudqk9696@naver.com").orElseThrow();

        return user.plusTestLike();
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 이메일을 가진 사용자를 찾을 수 없습니다 토큰을 다시 확인해주세요: " + email));
    }
}
