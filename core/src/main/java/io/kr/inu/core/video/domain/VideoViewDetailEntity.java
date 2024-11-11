package io.kr.inu.core.video.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "video_view_detail")
public class VideoViewDetailEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_view_detail_id")
    private Long id;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "view_time")
    private LocalDate viewTime;

    @Column(name = "video_view_id")
    private Long videoViewId;

    @Builder
    public VideoViewDetailEntity(LocalDate viewTime, Long viewCount, Long videoViewId) {
        this.viewTime = viewTime;
        this.viewCount = viewCount;
        this.videoViewId = videoViewId;
    }
}
