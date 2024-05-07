package io.kr.inu.webclient.api.user;

import io.kr.inu.core.user.dto.FindUserRespDto;
import io.kr.inu.core.user.service.UserService;
import io.kr.inu.webclient.api.resolver.UserEmail;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 조회", description = "JWT를 헤더에 넣어주세요.")
    @GetMapping(value = "/v1/find/user")
    public ResponseEntity<FindUserRespDto> findUser(UserEmail email) {
        return ResponseEntity.ok(userService.findUser(email.getEmail()));
    }
}
