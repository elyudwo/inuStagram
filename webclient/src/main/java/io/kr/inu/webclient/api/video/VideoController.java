package io.kr.inu.webclient.api.video;

import io.kr.inu.core.video.dto.MakeVideoReqDto;
import io.kr.inu.core.video.service.VideoService;
import io.kr.inu.webclient.api.resolver.UserEmail;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;

    @Operation(summary = "동영상 저장", description = "JWT를 헤더에 보내주세요. multipart로 보내주실때 key 값에 'video' 로 보내주세요")
    @PostMapping(value = "/v1/upload/video",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadImage(UserEmail email, @RequestPart MultipartFile video, @RequestBody String title) throws IOException {
        MakeVideoReqDto videoReqDto = MakeVideoReqDto.of(title, email.getEmail());

        return videoService.uploadVideo(video, videoReqDto);
    }
}
