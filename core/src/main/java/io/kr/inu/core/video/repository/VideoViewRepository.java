package io.kr.inu.core.video.repository;

import io.kr.inu.core.video.domain.VideoViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface VideoViewRepository extends JpaRepository<VideoViewEntity, Long> {

    Optional<VideoViewEntity> findByVideoId(Long videoId);
}
