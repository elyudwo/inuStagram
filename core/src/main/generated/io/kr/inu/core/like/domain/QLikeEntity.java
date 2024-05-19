package io.kr.inu.core.like.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeEntity is a Querydsl query type for LikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeEntity extends EntityPathBase<LikeEntity> {

    private static final long serialVersionUID = -170075457L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeEntity likeEntity = new QLikeEntity("likeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final io.kr.inu.core.user.domain.QUserEntity user;

    public final io.kr.inu.core.video.domain.QVideoEntity video;

    public QLikeEntity(String variable) {
        this(LikeEntity.class, forVariable(variable), INITS);
    }

    public QLikeEntity(Path<? extends LikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeEntity(PathMetadata metadata, PathInits inits) {
        this(LikeEntity.class, metadata, inits);
    }

    public QLikeEntity(Class<? extends LikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new io.kr.inu.core.user.domain.QUserEntity(forProperty("user")) : null;
        this.video = inits.isInitialized("video") ? new io.kr.inu.core.video.domain.QVideoEntity(forProperty("video")) : null;
    }

}

