package io.kr.inu.webclient.api.like;

import io.kr.inu.core.like.dto.EachVideoLikes;
import io.kr.inu.core.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/v1/get/likes")
    @Operation(summary = "동영상 좋아요 개수 조회", description = "쿼리 파라미터로 영상 식별자를 보내주세요.")
    public ResponseEntity<EachVideoLikes> getVideoLikes(@RequestParam Long videoId) {
        return ResponseEntity.ok(likeService.getVideoLike(videoId));
    }
}
