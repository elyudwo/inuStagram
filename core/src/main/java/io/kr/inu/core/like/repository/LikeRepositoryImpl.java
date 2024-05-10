package io.kr.inu.core.like.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.kr.inu.core.user.domain.UserEntity;
import io.kr.inu.core.video.dto.EachVideoData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static io.kr.inu.core.like.domain.QLikeEntity.likeEntity;
import static io.kr.inu.core.video.domain.QVideoEntity.videoEntity;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EachVideoData> findVideoByUserLikes(UserEntity user, Pageable pageable) {
        JPAQuery<EachVideoData> data = jpaQueryFactory
                .select(Projections.constructor(EachVideoData.class,
                        videoEntity.id,
                        videoEntity.videoUrl,
                        videoEntity.thumbnailUrl,
                        videoEntity.title))
                .from(likeEntity)
                .leftJoin(videoEntity).on(likeEntity.video.eq(videoEntity))
                .where(likeEntity.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return data.fetch();
    }
}
