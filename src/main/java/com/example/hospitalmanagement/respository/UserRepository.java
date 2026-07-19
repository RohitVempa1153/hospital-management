package com.example.hospitalmanagement.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospitalmanagement.entity.User;
import com.example.hospitalmanagement.entity.type.AuthProviderType;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
    
}
