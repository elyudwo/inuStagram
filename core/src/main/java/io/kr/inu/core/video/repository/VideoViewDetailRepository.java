package io.kr.inu.core.video.repository;

import io.kr.inu.core.video.domain.VideoViewDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoViewDetailRepository extends JpaRepository<VideoViewDetailEntity, Long> {
}
