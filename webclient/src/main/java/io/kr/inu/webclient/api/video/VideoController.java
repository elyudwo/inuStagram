package io.kr.inu.webclient.api.video;

import io.kr.inu.core.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;

    @Operation(summary = "동영상 저장", description = "동영상 정보를 보내주세요옹.")
    @PostMapping("/v1/upload/video")
    public ResponseEntity<HttpStatus> uploadImage(@RequestPart(name = "video", required = false) MultipartFile video) throws IOException {
        videoService.uploadVideo(video);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
