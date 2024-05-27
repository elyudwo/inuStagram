package io.kr.inu.core.user.domain;

import io.kr.inu.core.like.domain.LikeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_tb")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    private String color;

    @OneToMany(mappedBy = "user", cascade = REMOVE, fetch = LAZY)
    private List<LikeEntity> likes = new ArrayList<>();

    private Long testLike;

    @Builder
    public UserEntity(String email) {
        this.email = email;
        this.color = UserColor.issueRandomColor();
        this.testLike = 0L;
    }

    public Long plusTestLike() {
        testLike++;
        return testLike;
    }

}
