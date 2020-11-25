package com.github.flmn.distributedlock.redis;

import com.github.flmn.distributedlock.api.DistributedLock;
import com.github.flmn.util.RandomUtils;
import com.github.flmn.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Function;

public final class RedisDistributedLock implements DistributedLock {
    private static final String DEL_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
    private final StringRedisTemplate redisTemplate;

    public RedisDistributedLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;

        redisScript.setScriptText(DEL_LUA);
        redisScript.setResultType(Long.class);
    }

    @Override
    public <T> T doWithLock(String lockName, Duration expire, Function<Boolean, T> function) {
        if (StringUtils.isNullOrEmpty(lockName)) {
            throw new IllegalArgumentException(lockName + " can not be null or empty");
        }

        if (expire == null) {
            throw new IllegalArgumentException(expire + " can not be null");
        }

        boolean success = false;
        String randomValue = String.valueOf(RandomUtils.nextLong(1, Long.MAX_VALUE));
        try {
            Boolean ret = redisTemplate.opsForValue().setIfPresent(lockName, randomValue, expire);
            success = ret != null && ret;

            return function.apply(success);
        } finally {
            if (success) {
                Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockName), randomValue);
                if (result != null && result == 0) {
                    logger.warn("Lock {} expired.", lockName);
                }
            }
        }
    }
}
