package io.kr.inu.core.like.repository;

import io.kr.inu.core.like.domain.LikeEntity;
import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.video.domain.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long>, LikeRepositoryCustom {
    Long countByVideo_Id(Long videoId);
    Long countByUser_Id(Long userId);
    boolean existsByVideoAndUser(VideoEntity video, UserEntity user);
    Optional<LikeEntity> findByVideoAndUser(VideoEntity video, UserEntity user);
}
