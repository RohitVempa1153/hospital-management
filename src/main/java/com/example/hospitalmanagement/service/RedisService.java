package com.example.hospitalmanagement.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    private final String BLACKLIST_TOKEN_KEY = "token:blacklisted:";

    public void storeBlacklistToken(String jti, Date expiration)
    {
        long ttl = expiration.getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(BLACKLIST_TOKEN_KEY+jti, jti, Duration.ofMillis(ttl));
    }

    public Boolean isTokenBlacklisted(String jti)
    {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_TOKEN_KEY+jti));
    }
}
