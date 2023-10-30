package com.example.asm03spring.dao;

import com.example.asm03spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(int theId);
    Optional<User> findByName(String doctorName);
}
