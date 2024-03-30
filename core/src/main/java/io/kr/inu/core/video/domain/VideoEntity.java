package io.kr.inu.core.video.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "video")
public class VideoEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String videoUrl;

    private Long like;

    public static VideoEntity of(String email, String videoUrl) {
        return VideoEntity.builder()
                .email(email)
                .videoUrl(videoUrl)
                .like(0L)
                .build();
    }

    @Builder
    public VideoEntity(String email, String videoUrl, Long like) {
        this.email = email;
        this.videoUrl = videoUrl;
        this.like = like;
    }
}
