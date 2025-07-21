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

    public void signup(UserSignupRequestDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setName(dto.getName());
        userRepository.save(user);
    }
}
