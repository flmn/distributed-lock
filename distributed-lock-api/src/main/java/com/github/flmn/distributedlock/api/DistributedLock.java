package com.github.flmn.distributedlock.api;

import java.util.function.Supplier;

public interface DistributedLock {
    <T> T doWithLock(String lockName, Supplier<T> supplier);
}
