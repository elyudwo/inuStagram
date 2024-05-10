package io.kr.inu.core.like.repository;

import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.video.dto.EachVideoData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikeRepositoryCustom {

    List<EachVideoData> findVideoByUserLikes(UserEntity user, Pageable pageable);
}
