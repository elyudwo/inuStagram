package io.kr.inu.webclient.api.user;

import io.kr.inu.core.user.dto.FindUserRespDto;
import io.kr.inu.core.user.dto.UserLikeDto;
import io.kr.inu.core.user.service.UserService;
import io.kr.inu.webclient.api.resolver.UserEmail;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "회원 좋아요 조회", description = "좋아요를 조회하려는 회원의 이메일을 보내주세요")
    @PostMapping(value = "/v1/find/like")
    public ResponseEntity<Long> findUserLike(@RequestBody UserLikeDto userLikeDto) {
        return ResponseEntity.ok(userService.findLike(userLikeDto.getEmail()));
    }

    @Operation(summary = "회원 좋아요 1 추가", description = "좋아요를 추가하려는 회원의 이메일을 보내주세요")
    @PostMapping(value = "/v1/plus/like")
    public ResponseEntity<Void> plusLike(@RequestBody UserLikeDto userLikeDto) {
        userService.plusLike(userLikeDto.getEmail());
        return ResponseEntity.ok().build();
    }
}
