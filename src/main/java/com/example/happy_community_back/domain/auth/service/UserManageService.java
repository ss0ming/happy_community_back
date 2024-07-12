package com.example.happy_community_back.domain.auth.service;

import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyPasswordReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyNicknameReqDto;
import com.example.happy_community_back.domain.auth.dto.response.UserResDto;
import com.example.happy_community_back.domain.auth.entity.Member;
import com.example.happy_community_back.domain.auth.repository.MemberRepository;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserManageService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public UserResDto getUserInfo(final String email) {
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER_ID));

        return UserResDto.of(member);
    }

    @Transactional(readOnly = true)
    public boolean checkDuplicateEmail(final String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean checkDuplicateNickname(final String nickname) {
        boolean check = memberRepository.existsByNickname(nickname);
        System.out.println(check);
        return check;
    }

    @Transactional
    public void modifyPassword(UserModifyPasswordReqDto userModifyPasswordReqDto, final String email) {
        // 현재 로그인한 사용자의 이메일로 Member 객체를 조회
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER_ID));

        member.modifyPassword(userModifyPasswordReqDto);
        memberRepository.save(member);
    }

    @Transactional
    public void modifyNickname(UserModifyNicknameReqDto userModifyNicknameReqDto, final String email) {
        // 현재 로그인한 사용자의 이메일로 Member 객체를 조회
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER_ID));

        member.modifyNickname(userModifyNicknameReqDto);
        memberRepository.save(member);
    }
}