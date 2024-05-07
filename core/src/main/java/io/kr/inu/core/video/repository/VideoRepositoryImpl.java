package io.kr.inu.core.video.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.kr.inu.core.video.dto.EachVideoData;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static io.kr.inu.core.video.domain.QVideoEntity.videoEntity;

@RequiredArgsConstructor
public class VideoRepositoryImpl implements VideoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EachVideoData> findVideoByDate(Pageable pageable) {
        JPAQuery<EachVideoData> data = jpaQueryFactory
                .select(Projections.constructor(EachVideoData.class,
                        videoEntity.videoUrl,
                        videoEntity.thumbnailUrl,
                        videoEntity.title,
                        videoEntity.likeCount))
                .from(videoEntity)
                .orderBy(videoEntity.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return data.fetch();
    }
}
