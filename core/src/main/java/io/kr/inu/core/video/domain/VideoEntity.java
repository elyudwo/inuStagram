package io.kr.inu.core.video.domain;

import io.kr.inu.core.common.BaseTimeEntity;
import io.kr.inu.core.like.domain.LikeEntity;
import io.kr.inu.core.video.dto.MakeVideoReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "video")
public class VideoEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    private String email;

    @Column(length = 1000)
    private String videoUrl;

    @Column(length = 1000)
    private String thumbnailUrl;

    private String title;

    @OneToMany(mappedBy = "video", cascade = REMOVE, fetch = LAZY)
    private List<LikeEntity> likes = new ArrayList<>();

    @Builder
    public VideoEntity(String email, String videoUrl, String thumbnailUrl, String title) {
        this.email = email;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
    }

    public static VideoEntity of(String videoUrl, String thumbnailUrl, MakeVideoReqDto videoReqDto) {
        return VideoEntity.builder()
                .email(videoReqDto.getEmail())
                .videoUrl(videoUrl)
                .thumbnailUrl(thumbnailUrl)
                .title(videoReqDto.getTitle())
                .build();
    }
}
