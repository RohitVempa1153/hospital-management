package com.example.hospitalmanagement.security;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.hospitalmanagement.dto.LoginRequestDto;
import com.example.hospitalmanagement.dto.LoginResponseDto;
import com.example.hospitalmanagement.dto.SignupRequestDto;
import com.example.hospitalmanagement.dto.SignupResponseDto;
import com.example.hospitalmanagement.entity.User;
import com.example.hospitalmanagement.entity.type.AuthProviderType;
import com.example.hospitalmanagement.entity.type.RoleType;
import com.example.hospitalmanagement.respository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public User signupInternal(SignupRequestDto signupRequestDto, AuthProviderType authProviderType, String providerId)
    {
        var user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("User already exists");
        }

        user = User.builder()
            .username(signupRequestDto.getUsername())
            .providerType(authProviderType)
            .providerId(providerId)
            .roles(signupRequestDto.getRoles())
            .build();

        if (authProviderType == AuthProviderType.EMAIL) {
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        return userRepository.save(user);

    }

    // Signup from controller
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User newUser = signupInternal(signupRequestDto, AuthProviderType.EMAIL, null);
        return new SignupResponseDto(newUser.getId(), newUser.getUsername());
    } 

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        // Fetch ProviderId: "sub", "id" and ProviderType: "google", "github", "facebook"
        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        String email = oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUsername(email).orElse(null); 

        if (user == null && emailUser == null) {
            //Sign up flow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            log.info("User's email: {}",username);
            log.info("User's info: {}", oAuth2User);
            user = signupInternal(new SignupRequestDto(username, null, Set.of(RoleType.PATIENT)), providerType, providerId);
        }
        else if(user != null)
        {
            // Update the email if email has not been provided before
            if (email != null && !email.isBlank () && !email.equals(user.getUsername()) ) {
                user.setUsername(email);
                userRepository.save(null); 
            }
            // Login
        }
        else
        {
            /* 
                emailUser != null
                Suppose user has already logged in using a github provider and now the user is trying 
                to login with google provider with the same email with OAuth then we shouldn't allow the user to login or signup 
            */
            throw new BadCredentialsException ("This email is already registered with provider: " + emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());
        return ResponseEntity.ok(loginResponseDto); 
    }
}
