package io.kr.inu.core.like.service;

import io.kr.inu.core.like.dto.EachVideoLikes;
import io.kr.inu.core.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public EachVideoLikes getVideoLike(Long videoId) {
        Long likes = likeRepository.countByVideo_Id(videoId);

        return EachVideoLikes.builder()
                .like(likes)
                .build();
    }
}
