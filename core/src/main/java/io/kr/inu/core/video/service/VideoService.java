package io.kr.inu.core.video.service;

import io.kr.inu.infra.s3.ImageRepository;
import io.kr.inu.infra.s3.MultipartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final ImageRepository imageRepository;

    @Transactional
    public void uploadVideo(MultipartFile video) throws IOException {
        MultipartDto multipartDto = new MultipartDto(video.getName(), video.getSize(), video.getContentType(), video.getInputStream());
        imageRepository.saveVideo(multipartDto);

    }
}
