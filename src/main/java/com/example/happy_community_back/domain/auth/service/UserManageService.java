package com.example.happy_community_back.domain.auth.service;

import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyPasswordReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyNicknameReqDto;
import com.example.happy_community_back.domain.auth.dto.response.UserResDto;
import com.example.happy_community_back.domain.auth.entity.Member;
import com.example.happy_community_back.domain.auth.repository.MemberRepository;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserManageService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public UserResDto getUserInfo(final String email) {
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER_ID));

        String imagePath = member.getProfileImage();

        String base64Image = null;
        File file = new File(imagePath);
        if (file.exists()) {
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                base64Image = Base64.getEncoder().encodeToString(fileContent);
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_READ_ERROR);
            }
        }

        return UserResDto.of(member, base64Image);
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