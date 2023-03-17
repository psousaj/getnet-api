package com.psousaj.getnetapi.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psousaj.getnetapi.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
}
