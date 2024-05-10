package io.kr.inu.core.video.repository;

import io.kr.inu.core.video.dto.EachVideoData;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepositoryCustom {

    List<EachVideoData> findVideoByDate(Pageable pageable);
    List<EachVideoData> findVideoByEmail(String email, Pageable pageable);

}
