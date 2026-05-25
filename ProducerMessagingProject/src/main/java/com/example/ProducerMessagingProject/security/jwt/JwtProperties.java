package com.example.ProducerMessagingProject.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String jwtKey,
        long jwtExpirationMinutes,
        String jwtSignatureAlgorithm){

}
