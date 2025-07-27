package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.User;
import com.tierguardians.finances.repository.UserRepository;
import com.tierguardians.finances.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public Map<String, String> reissueTokens(String refreshToken) {
        // 1. 리프레시 토큰 검증
        String userId = jwtUtil.validateAndGetUserId(refreshToken);

        // 2. DB에 저장된 토큰과 비교
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 3. 토큰 재발급
        String newAccessToken = jwtUtil.generateAccessToken(userId);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId); // 갱신

        // 4. DB 저장
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        );
    }
}
