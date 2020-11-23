package com.github.flmn.distributedlock.examples.service;

import com.github.flmn.distributedlock.api.DistributedLock;
import org.springframework.stereotype.Service;

@Service
public class LockService {
    private final DistributedLock distributedLock;

    public LockService(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }
}
