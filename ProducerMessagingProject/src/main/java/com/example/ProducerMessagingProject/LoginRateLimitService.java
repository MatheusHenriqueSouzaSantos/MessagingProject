package com.example.ProducerMessagingProject;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginRateLimitService {
    private final Map<String, Bucket> bucketsByIp=new ConcurrentHashMap<String,Bucket>();

    public void registerFailedAttempt(String ip) {
        bucketsByIp.computeIfAbsent(ip, i -> createBucket()).tryConsume(1);
    }

    public boolean isBlocked(String ip) {
        Bucket bucket = bucketsByIp.get(ip);
        return bucket != null && !(bucket.getAvailableTokens() > 0);
    }

    public Bucket createBucket(){
        Bandwidth limit= Bandwidth.builder()
                .capacity(3)
                .refillIntervally(3, Duration.ofMinutes(1))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
