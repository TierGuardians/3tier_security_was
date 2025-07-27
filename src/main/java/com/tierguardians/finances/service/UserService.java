package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.User;
import com.tierguardians.finances.dto.*;
import com.tierguardians.finances.repository.AssetRepository;
import com.tierguardians.finances.repository.BudgetRepository;
import com.tierguardians.finances.repository.ExpenseRepository;
import com.tierguardians.finances.repository.UserRepository;
import com.tierguardians.finances.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final AssetRepository assetRepository;
    private final ExpenseRepository expenseRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입 기능
    public void signup(UserSignupRequestDto dto) {
        // 사용자 ID 중복 확인
        if (userRepository.existsById(dto.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        // 이메일 중복 확인
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 사용자 엔티티 생성 및 저장
        User user = new User();
        user.setUserId(dto.getUserId());
        // 비밀번호 암호화 부분
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        userRepository.save(user);
    }

    // 로그인 기능
    public Map<String, String> login(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    // 내 정보 조회 기능
    public MyPageResponseDto getMyPage(String userId) {
        // 사용자 정보 조회 및 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        UserInfoDto userInfo = UserInfoDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();

        // 예산
        List<BudgetDto> budgets = budgetRepository.findByUserId(userId).stream()
                .map(b -> BudgetDto.builder()
                        .id(b.getId())
                        .month(b.getMonth())
                        .amount(b.getAmount())
                        .build())
                .toList();

        // 자산
        List<AssetDto> assets = assetRepository.findByUserId(userId).stream()
                .map(a -> AssetDto.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .type(a.getType())
                        .amount(a.getAmount())
                        .build())
                .toList();

        // 소비
        List<ExpenseDto> expenses = expenseRepository.findByUserId(userId).stream()
                .map(e -> ExpenseDto.builder()
                        .id(e.getId())
                        .category(e.getCategory())
                        .description(e.getDescription())
                        .amount(e.getAmount())
                        .spentAt(LocalDate.ofEpochDay(e.getSpentAt().toEpochDay()))
                        .build())
                .toList();

        return MyPageResponseDto.builder()
                .user(userInfo)
                .budgets(budgets)
                .assets(assets)
                .expenses(expenses)
                .build();
    }
}
