package io.kr.inu.webclient.api.video;

import io.kr.inu.core.common.Timer;
import io.kr.inu.core.video.dto.FindVideoResponseDto;
import io.kr.inu.core.video.dto.MakeVideoReqDto;
import io.kr.inu.core.video.dto.UploadVideoReqDto;
import io.kr.inu.core.video.service.VideoService;
import io.kr.inu.webclient.api.resolver.UserEmail;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;

    @Operation(summary = "동영상 저장", description = "JWT를 헤더에 보내주십쇼. multipart로 보내주실때 key 값에 'video' 로 보내주세요")
    @PostMapping(value = "/v1/upload/video",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timer
    public String uploadVideo(UserEmail email, @RequestPart(name = "video") MultipartFile video, @RequestPart(name = "title") String title) throws IOException {
        MakeVideoReqDto videoReqDto = MakeVideoReqDto.of(title, email.getEmail());

        return videoService.uploadVideo(video, videoReqDto);
    }

    @Operation(summary = "동영상 최신순 조회 기능", description = "JWT를 헤더에 보내주세요. 조회하려는 페이지와 동영상 개수를 입력해주세요.")
    @GetMapping(value = "/v1/find/video")
    public ResponseEntity<FindVideoResponseDto> findVideoByDate(UserEmail email, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(videoService.findVideoByDate(email.getEmail(), page, size));
    }

    @Operation(summary = "내가 올린 동영상 조회 기능", description = "JWT를 헤더에 보내주세요. 조회하려는 페이지와 동영상 개수를 입력해주세요.")
    @GetMapping(value = "/v1/find/video/myself")
    public ResponseEntity<FindVideoResponseDto> findVideoByEmail(UserEmail email, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(videoService.findVideoByEmail(email.getEmail(), page, size));
    }

    @Operation(summary = "presigned-url 발급 API")
    @GetMapping(value = "/v1/generate-presigned-url/{fileName}")
    public ResponseEntity<Map<String, String>> getPresignedUrl(@PathVariable(name = "fileName") String fileName) {
        Map<String, String> presignedUrl = videoService.getPresignedUrl("mp4", fileName);

        return ResponseEntity.ok(presignedUrl);
    }

    @Operation(summary = "영상 URL 저장 API")
    @PostMapping(value = "/v2/upload/video")
    public ResponseEntity<Void> uploadVideo(UserEmail email, @RequestBody UploadVideoReqDto dto) {
        videoService.uploadVideoUrl(email.getEmail(), dto);
        return ResponseEntity.ok().build();
    }

}
