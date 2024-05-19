package io.kr.inu.core.video.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideoEntity is a Querydsl query type for VideoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideoEntity extends EntityPathBase<VideoEntity> {

    private static final long serialVersionUID = 630365497L;

    public static final QVideoEntity videoEntity = new QVideoEntity("videoEntity");

    public final io.kr.inu.core.common.QBaseTimeEntity _super = new io.kr.inu.core.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<io.kr.inu.core.like.domain.LikeEntity, io.kr.inu.core.like.domain.QLikeEntity> likes = this.<io.kr.inu.core.like.domain.LikeEntity, io.kr.inu.core.like.domain.QLikeEntity>createList("likes", io.kr.inu.core.like.domain.LikeEntity.class, io.kr.inu.core.like.domain.QLikeEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final StringPath videoUrl = createString("videoUrl");

    public QVideoEntity(String variable) {
        super(VideoEntity.class, forVariable(variable));
    }

    public QVideoEntity(Path<? extends VideoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideoEntity(PathMetadata metadata) {
        super(VideoEntity.class, metadata);
    }

}

