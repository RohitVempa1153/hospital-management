package com.example.hospitalmanagement.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.hospitalmanagement.entity.type.AuthProviderType;
import com.example.hospitalmanagement.entity.type.PermissionType;
import com.example.hospitalmanagement.entity.type.RoleType;
import com.example.hospitalmanagement.security.RolePermissionMapping;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "app_user", indexes = {
    @Index(name="idx_provider_id_provider_type", columnList = "providerId, providerType")
})
@Builder
@NoArgsConstructor
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;
    
    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RoleType> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return roles.stream()
        // .map((role)-> new SimpleGrantedAuthority("ROLE_"+role))
        // .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach((role)->{
            Set<SimpleGrantedAuthority> permissions = RolePermissionMapping.getPermissionForRole(role);
            authorities.addAll(permissions);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });

        return authorities;
    }
}
