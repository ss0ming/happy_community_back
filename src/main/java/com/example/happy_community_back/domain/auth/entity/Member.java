package com.example.happy_community_back.domain.auth.entity;

import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyPasswordReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyNicknameReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserSignUpReqDto;
import com.example.happy_community_back.global.config.db.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Comment("이메일")
    private String email;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(nullable = false)
    @Comment("닉네임")
    private String nickname;

    @Column(name = "image")
    @Comment("프로필 사진")
    private String profileImage;

    @Builder
    public Member (String email, String password, String nickname, String image) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = image;
    }

    public static Member of(UserSignUpReqDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .image(dto.getImage())
                .build();
    }

    public void modifyPassword(UserModifyPasswordReqDto dto) {
        this.password = dto.password();
    }

    public void modifyNickname(UserModifyNicknameReqDto dto) {
        this.nickname = dto.nickname();
    }
}
