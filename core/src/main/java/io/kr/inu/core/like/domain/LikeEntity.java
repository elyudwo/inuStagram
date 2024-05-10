package io.kr.inu.core.like.domain;

import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.video.domain.VideoEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "like_tb")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoEntity video;

    @Builder
    public LikeEntity(UserEntity user, VideoEntity video) {
        this.user = user;
        this.video = video;
    }
}
