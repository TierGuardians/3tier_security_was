package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.User;
import com.tierguardians.finances.dto.UserSignupRequestDto;
import com.tierguardians.finances.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    // 회원가입 기능
    public void signup(UserSignupRequestDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        // 평문 저장
        user.setPassword(dto.getPassword());
        // 비밀번호 암호화 부분
        //user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setName(dto.getName());
        userRepository.save(user);
    }

    // 로그인 기능
    public boolean login(String userId, String password) {
        return userRepository.findById(userId)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
