package com.example.hospitalmanagement.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.dto.LoginRequestDto;
import com.example.hospitalmanagement.dto.LoginResponseDto;
import com.example.hospitalmanagement.dto.SignupResponseDto;
import com.example.hospitalmanagement.entity.User;
import com.example.hospitalmanagement.respository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public LoginResponseDto login(LoginRequestDto loginRequestDto)
    {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        var token = authUtil.generateAccessToken(user);
        return new LoginResponseDto(token, user.getId());
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        var user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("User already exists");
        }

        var newUser = userRepository.save(User.builder()
            .username(signupRequestDto.getUsername())
            .password(passwordEncoder.encode(signupRequestDto.getPassword()))
            .build()
        );

        return new SignupResponseDto(newUser.getId(), newUser.getUsername());
    }
}
