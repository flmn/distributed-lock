package com.github.flmn.distributedlock.api;

import java.time.Duration;
import java.util.function.Function;

public interface DistributedLock {
    <T> T doWithLock(String lockName, Duration expire, Function<Boolean, T> function);
}
