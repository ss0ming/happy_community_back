package com.example.happy_community_back.domain.auth.service;

import com.example.happy_community_back.domain.auth.dto.request.LoginReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserSignUpReqDto;
import com.example.happy_community_back.domain.auth.entity.Member;
import com.example.happy_community_back.domain.auth.enums.Role;
import com.example.happy_community_back.domain.auth.enums.TokenName;
import com.example.happy_community_back.domain.auth.repository.MemberRepository;
import com.example.happy_community_back.global.auth.jwt.component.JwtTokenProvider;
import com.example.happy_community_back.global.auth.jwt.dto.AccessTokenDto;
import com.example.happy_community_back.global.auth.jwt.dto.RefreshTokenDto;
import com.example.happy_community_back.global.auth.jwt.dto.TokenDto;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import com.example.happy_community_back.global.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public AccessTokenDto login(LoginReqDto loginReqDto, HttpServletResponse response) {
        // email로 Member 조회
        Member savedMember = memberRepository.findById(loginReqDto.email())
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_TO_LOGIN));

        // Password 일치 여부 확인
        if (!passwordEncoder.matches(loginReqDto.password(), savedMember.getPassword())) {
            throw new CustomException(ErrorCode.FAILED_TO_LOGIN);
        }

        // Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedMember.getEmail(), savedMember.getPassword(), Collections.singletonList(Role.ROLE_USER::name));

        // 인증 정보를 기반으로 JWT Token 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        RefreshTokenDto refreshTokenDto = tokenDto.refreshTokenDto();

        // Refresh Token을 쿠키에 담아서 전달
        Cookie cookie = CookieUtils.createCookie(TokenName.USER_REFRESH_TOKEN.name(),  refreshTokenDto.token(), refreshTokenDto.getExpiresInSecond());
        response.addCookie(cookie);

        return tokenDto.accessTokenDto();
    }

    @Transactional
    public void signUp(UserSignUpReqDto dto) {
        // 아이디 중복 체크
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        // 비밀번호 일치 여부 체크
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
        }

        // 비밀번호 암호화
        dto.modifyPassword(passwordEncoder.encode(dto.getPassword()));

        // 프로필 사진
        String profileImageBase64 = dto.getImage();
        if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
            // Base64 헤더 제거
            String base64Image = profileImageBase64.split(",")[1];
            try {
                // Base64 디코딩
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                // 파일 저장
                File file = new File("image/profile/" + dto.getEmail() + ".png");
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(imageBytes);
                } catch (Exception e) {
                    throw new CustomException(ErrorCode.SEVER_ERROR);
                }

                System.out.println("이미지 저장 성공: " + file.getAbsolutePath());

                dto.modifyImage(file.getAbsolutePath());
            } catch (Exception e) {
                throw new CustomException(ErrorCode.SEVER_ERROR);
            }
        }

        Member member = Member.of(dto);
        memberRepository.save(member);
    }

    /**
     * 로그아웃
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Refresh Token 쿠키 삭제
        Cookie cookie = CookieUtils.getCookieForRemove(TokenName.USER_REFRESH_TOKEN.name());
        response.addCookie(cookie);
    }
}
