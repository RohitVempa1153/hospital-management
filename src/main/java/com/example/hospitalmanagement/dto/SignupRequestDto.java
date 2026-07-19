package com.example.hospitalmanagement.dto;

import java.util.Set;

import com.example.hospitalmanagement.entity.type.RoleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequestDto {
    @NotNull(message = "username is required")
    @NotBlank
    private String username;
    @NotNull(message = "password is required")
    @NotBlank
    private String password;
    @NotNull
    @NotEmpty
    private Set<RoleType> roles;
}
