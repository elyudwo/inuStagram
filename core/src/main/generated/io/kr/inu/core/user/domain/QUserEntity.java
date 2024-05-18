package io.kr.inu.core.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -737069273L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath color = createString("color");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<io.kr.inu.core.like.domain.LikeEntity, io.kr.inu.core.like.domain.QLikeEntity> likes = this.<io.kr.inu.core.like.domain.LikeEntity, io.kr.inu.core.like.domain.QLikeEntity>createList("likes", io.kr.inu.core.like.domain.LikeEntity.class, io.kr.inu.core.like.domain.QLikeEntity.class, PathInits.DIRECT2);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

