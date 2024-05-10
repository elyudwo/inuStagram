package io.kr.inu.core.video.repository;

import io.kr.inu.core.video.domain.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long>, VideoRepositoryCustom {

    Long countByEmail(String email);
}
