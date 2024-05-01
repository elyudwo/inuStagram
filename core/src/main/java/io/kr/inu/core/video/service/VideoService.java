package io.kr.inu.core.video.service;

import io.kr.inu.core.common.Converter;
import io.kr.inu.core.video.domain.VideoEntity;
import io.kr.inu.core.video.dto.EachVideoData;
import io.kr.inu.core.video.dto.FindVideoResponseDto;
import io.kr.inu.core.video.dto.MakeVideoReqDto;
import io.kr.inu.core.video.repository.VideoRepository;
import io.kr.inu.infra.s3.VideoS3Repository;
import io.kr.inu.infra.s3.MultipartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoS3Repository videoS3Repository;
    private final VideoRepository videoRepository;
    private final VideoValidateService videoValidateService;

    @Transactional
    public String uploadVideo(MultipartFile video, MakeVideoReqDto videoReqDto) throws IOException {
        MultipartDto multipartDto = new MultipartDto(video.getOriginalFilename(), video.getSize(), video.getContentType(), video.getInputStream());
        String videoUrl = videoS3Repository.saveVideo(multipartDto);
//        videoValidateService.validateHarmVideo(video);

        videoRepository.save(VideoEntity.of(videoUrl, videoReqDto));

        return videoUrl;
    }

    public FindVideoResponseDto findVideoByDate(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<EachVideoData> posts = videoRepository.findVideoByDate(pageable);
        boolean next = isNext(videoRepository.count(), page, size);;

        return FindVideoResponseDto.builder()
                .allVideos(posts)
                .next(next)
                .build();
    }

    private boolean isNext(long count, int page, int size) {
        if((long) size * page + page < count) {
            return true;
        }
        return false;
    }
}
