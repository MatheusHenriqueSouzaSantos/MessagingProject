package com.example.ProducerMessagingProject;

import com.example.ProducerMessagingProject.user.UserModel;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtGenerate {
    private final JwtProperties jwtProperties;

    private final JwtEncoder jwtEncoder;

    public JwtGenerate(JwtProperties jwtProperties, JwtEncoder jwtEncoder) {
        this.jwtProperties = jwtProperties;
        this.jwtEncoder = jwtEncoder;

    }

    public String generateToken(UserModel user) {
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .claim("userName", user.getUserName())
                .claim("email", user.getEmail())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(jwtProperties.jwtExpirationMinutes(), ChronoUnit.MINUTES))
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();
    }
}
