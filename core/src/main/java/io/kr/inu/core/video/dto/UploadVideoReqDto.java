package io.kr.inu.core.video.dto;

import io.kr.inu.core.video.domain.VideoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UploadVideoReqDto {

    private String videoUrl;
    private String thumbnailUrl;
    private String title;


    public VideoEntity convertDtoToEntity(String userEmail) {
        return VideoEntity.builder()
                .email(userEmail)
                .thumbnailUrl(thumbnailUrl)
                .videoUrl(videoUrl)
                .title(title)
                .build();
    }
}
