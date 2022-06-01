package cn.bootx.common.lock.service.impl;

import cn.bootx.common.lock.entity.LockInfo;
import cn.bootx.common.lock.service.LockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 读锁实现
 *
 * @author xianzhi.chen@hand-china.com 2019年1月14日下午7:23:12
 */
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class ReadLockServiceImpl implements LockService {

    private final RedissonClient redissonClient;

    private final ThreadLocal<LockInfo> lockInfoThreadLocal = new ThreadLocal<>();

    @Override
    public void setLockInfo(LockInfo lockInfo) {
        lockInfoThreadLocal.set(lockInfo);
    }

    @Override
    public LockInfo getLockInfo() {
        return lockInfoThreadLocal.get();
    }

    @Override
    public void clearLockInfo() {
        lockInfoThreadLocal.remove();
    }

    @Override
    public boolean lock() {
        LockInfo lockInfo = lockInfoThreadLocal.get();
        try {
            RReadWriteLock rLock = redissonClient.getReadWriteLock(lockInfo.getName());
            return rLock.readLock().tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), lockInfo.getTimeUnit());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void releaseLock() {
        LockInfo lockInfo = lockInfoThreadLocal.get();
        RReadWriteLock rLock = redissonClient.getReadWriteLock(lockInfo.getName());
        if (rLock.readLock().isHeldByCurrentThread()) {
            rLock.readLock().unlockAsync();
        }
        lockInfoThreadLocal.remove();
    }
}