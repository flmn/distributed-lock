package com.github.flmn.distributedlock.redis.configuration;

import com.github.flmn.distributedlock.api.DistributedLock;
import com.github.flmn.distributedlock.redis.RedisDistributedLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@ComponentScan("com.github.flmn.distributedlock.redis")
@ConditionalOnProperty(prefix = "distributed-lock", name = "backend", havingValue = "redis")
public class RedisDistributedLockAutoConfiguration {

    @Bean
    public DistributedLock distributedLock(StringRedisTemplate redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }
}
