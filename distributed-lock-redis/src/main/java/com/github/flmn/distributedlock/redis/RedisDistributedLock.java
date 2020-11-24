package com.github.flmn.distributedlock.redis;

import com.github.flmn.distributedlock.api.DistributedLock;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.function.Supplier;

public final class RedisDistributedLock implements DistributedLock {
    private final StringRedisTemplate redisTemplate;

    public RedisDistributedLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> T doWithLock(String lockName, Supplier<T> supplier) {
        return null;
    }
}
