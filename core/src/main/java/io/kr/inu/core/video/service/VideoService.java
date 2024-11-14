package io.kr.inu.core.video.service;

import io.kr.inu.core.common.ratelimiter.RateLimiter;
import io.kr.inu.core.user.service.UserValidateService;
import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.dto.EachVideoData;
import io.kr.inu.core.video.dto.FindVideoResponseDto;
import io.kr.inu.core.video.dto.MakeVideoReqDto;
import io.kr.inu.core.video.dto.UploadVideoReqDto;
import io.kr.inu.core.video.repository.VideoRepository;
import io.kr.inu.core.video.repository.VideoViewRepository;
import io.kr.inu.infra.s3.S3Service;
import io.kr.inu.infra.s3.VideoS3Repository;
import io.kr.inu.infra.s3.MultipartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final VideoS3Repository videoS3Repository;
    private final VideoRepository videoRepository;
    private final VideoValidateService videoValidateService;
    private final UserValidateService userValidateService;
    private final S3Service s3Service;
    private final VideoViewService videoViewService;
    private final VideoViewRepository videoViewRepository;
    @Value("${ffmpeg.dir.ffmpeg}")
    private String ffmpeg;
    @Value("${ffmpeg.dir.ffprobe}")
    private String ffprobe;

    @Transactional
    @RateLimiter(key = "#email", duration = 60)
    public String uploadVideo(MultipartFile video, MakeVideoReqDto videoReqDto, String email) throws IOException {
        userValidateService.existUserByEmail(videoReqDto.getEmail());
        MultipartDto multipartDto = new MultipartDto(video.getOriginalFilename(), video.getSize(), video.getContentType(), video.getInputStream());
        String videoUrl = videoS3Repository.saveVideo(multipartDto);
//        String thumbnailUrl = videoS3Repository.saveVideoByStream(multipartDto.getOriginalFileName() + "thumbnail", extractThumbnail(video));
//        videoValidateService.validateHarmVideo(new HarmfulVideoReqDto(videoEntity.getId(), video.getOriginalFilename()));
//        videoValidateService.validateHarmVideo(video);
        VideoEntity videoEntity = videoRepository.save(VideoEntity.of(videoUrl, "thumbnailUrl", videoReqDto));

        videoViewService.createVideoView(videoEntity);
        return videoUrl;
    }

    @Transactional
    @RateLimiter(key = "#email", duration = 60)
    public void uploadVideoUrl(String userEmail, UploadVideoReqDto dto) {
        userValidateService.existUserByEmail(userEmail);
        videoRepository.save(dto.convertDtoToEntity(userEmail));
    }

    private File extractThumbnail(MultipartFile videoFile) throws IOException {
        FFmpeg ffMpeg = new FFmpeg(ffmpeg);
        FFprobe ffProbe = new FFprobe(ffprobe);

        File outputThumbnailFile = File.createTempFile("temp_", ".jpg");

        Path tempFilePath = outputThumbnailFile.toPath();
        Files.copy(videoFile.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

        File thumbnailOutputFile = File.createTempFile("thumbnail_", ".jpg");
        thumbnailOutputFile.deleteOnExit();

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(outputThumbnailFile.toString())
                .overrideOutputFiles(true)
                .addOutput(thumbnailOutputFile.getAbsolutePath())
                .setFrames(1)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffMpeg, ffProbe);
        executor.createJob(builder).run();

        return thumbnailOutputFile;
    }

    public FindVideoResponseDto findVideoByDate(String email, int page, int size) {
        userValidateService.existUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        List<EachVideoData> posts = videoRepository.findVideoByDate(pageable);
        boolean next = isNext(videoRepository.count(), page, size);

        return FindVideoResponseDto.builder()
                .allVideos(posts)
                .next(next)
                .build();
    }

    private boolean isNext(long count, int page, int size) {
        return (long) size * page + size < count;
    }

    public FindVideoResponseDto findVideoByEmail(String email, int page, int size) {
        userValidateService.existUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        List<EachVideoData> posts = videoRepository.findVideoByEmail(email, pageable);
        boolean next = isNext(videoRepository.count(), page, size);

        return FindVideoResponseDto.builder()
                .allVideos(posts)
                .next(next)
                .build();
    }

    public Map<String, String> getPresignedUrl(String prefix, String fileName) {
        return s3Service.getPresignedUrl(prefix, fileName);
    }

    public VideoEntity getVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 식별자를 가진 비디오를 찾을 수 업습니다"));
    }
}
