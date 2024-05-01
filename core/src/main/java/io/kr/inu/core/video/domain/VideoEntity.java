package io.kr.inu.core.video.domain;

import io.kr.inu.core.common.BaseTimeEntity;
import io.kr.inu.core.video.dto.MakeVideoReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "video")
public class VideoEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String videoUrl;

    private String title;

    @Column(name = "video_like")
    private Long like;


    @Builder
    public VideoEntity(String email, String videoUrl, String title, Long like) {
        this.email = email;
        this.videoUrl = videoUrl;
        this.title = title;
        this.like = like;
    }

    public static VideoEntity of(String videoUrl, MakeVideoReqDto videoReqDto) {
        return VideoEntity.builder()
                .email(videoReqDto.getEmail())
                .videoUrl(videoUrl)
                .title(videoReqDto.getTitle())
                .like(0L)
                .build();
    }
}
