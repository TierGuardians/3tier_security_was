package com.tierguardians.finances.repository;

import com.tierguardians.finances.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
