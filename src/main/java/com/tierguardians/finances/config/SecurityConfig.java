package com.tierguardians.finances.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 테스트 시에는 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**", "/assets/**","/budgets/**").permitAll()
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .httpBasic(); // 또는 .formLogin() 도 가능

        return http.build();
    }
}

