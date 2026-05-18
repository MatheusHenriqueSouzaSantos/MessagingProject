package com.example.ProducerMessagingProject;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String jwtKey,
        long jwtExpirationMinutes,
        String jwtSignatureAlgorithm){

}
