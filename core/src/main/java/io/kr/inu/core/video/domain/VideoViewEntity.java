package io.kr.inu.core.video.domain;

import io.kr.inu.core.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "video_view")
public class VideoViewEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_view_id")
    private Long id;

    private Long viewCount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "video_id")
    private VideoEntity video;

    @Builder
    public VideoViewEntity(Long viewCount, VideoEntity video) {
        this.viewCount = viewCount;
        this.video = video;
    }
}
