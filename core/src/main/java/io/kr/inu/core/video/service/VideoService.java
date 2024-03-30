package io.kr.inu.core.video.service;

import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.repository.VideoRepository;
import io.kr.inu.infra.s3.VideoS3Repository;
import io.kr.inu.infra.s3.MultipartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoS3Repository videoS3Repository;
    private final VideoRepository videoRepository;

    @Transactional
    public String uploadVideo(MultipartFile video, String email) throws IOException {
        MultipartDto multipartDto = new MultipartDto(video.getName(), video.getSize(), video.getContentType(), video.getInputStream());
        String videoUrl = videoS3Repository.saveVideo(multipartDto);
        videoRepository.save(VideoEntity.of(email, videoUrl));

        return videoUrl;
    }
}
