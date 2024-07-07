package com.example.happy_community_back.domain.auth.repository;

import com.example.happy_community_back.domain.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
