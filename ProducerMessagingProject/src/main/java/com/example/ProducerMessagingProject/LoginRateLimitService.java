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

    public boolean allowRequest(String ip){
        Bucket bucket=bucketsByIp.computeIfAbsent(
                ip,
                i ->createBucket()
        );
        return bucket.tryConsume(1);
    }

    public Bucket createBucket(){
        Bandwidth limit= Bandwidth.builder()
                .capacity(3)
                .refillGreedy(3, Duration.ofMinutes(1))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
