package com.example.hospitalmanagement.security;

import static com.example.hospitalmanagement.entity.type.RoleType.*;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.hospitalmanagement.entity.type.PermissionType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        httpSecurity
        .authorizeHttpRequests(auth->auth
            .requestMatchers("/public/**").permitAll()
            .requestMatchers("/admin/**").hasRole(ADMIN.name())
            .requestMatchers(HttpMethod.DELETE, "/admin/**").hasAuthority(PermissionType.APPOINTMENT_DELETE.name())
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(), ADMIN.name())
            .anyRequest().authenticated()
        )
        .formLogin(formLogin->
            formLogin.disable()
        )
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .oauth2Login(oauth2LoginCustomizer -> oauth2LoginCustomizer
            .failureHandler(
            (request, response, exception)->{
                log.error("OAuth2 error: "+exception.getMessage());
                handlerExceptionResolver.resolveException(request, response, null, exception);
            })
            .successHandler(oAuth2SuccessHandler)
        )
        .exceptionHandling(exceptionHandlingConfigurer -> 
            exceptionHandlingConfigurer.accessDeniedHandler((request, response, exception)->{
                handlerExceptionResolver.resolveException(request, response, null, exception);
            })
        )
        ;

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
