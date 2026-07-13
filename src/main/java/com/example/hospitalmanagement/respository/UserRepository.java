package com.example.hospitalmanagement.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospitalmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    
}
