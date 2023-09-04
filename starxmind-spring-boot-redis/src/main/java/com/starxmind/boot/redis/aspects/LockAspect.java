package com.starxmind.boot.redis.aspects;

import com.starxmind.boot.redis.annotation.Lock;
import com.starxmind.piano.redis.DistributedLock;
import com.starxmind.piano.redis.DistributedLockFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class LockAspect extends AbstractAspect {
    private final DistributedLockFactory distributedLockFactory;

    @Pointcut("@annotation(com.starxmind.boot.redis.annotation.Lock)")
    public void pointCut() {
        log.debug("<LockAspect.pointCut> Declare the pointcut...");
    }

    @Around("pointCut() && @annotation(lock)")
    public Object execute(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        log.debug("<LockAspect.execute> Lock begin...");

        String lockName = getLockName(joinPoint, lock.value());
        DistributedLock distributedLock = distributedLockFactory.get(lockName);

        Object proceed;
        try {
            distributedLock.lock(lock.leaseTime(), lock.unit());
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            distributedLock.unlock();
        }

        log.debug("<LockAspect.execute> Lock end.");
        return proceed;
    }
}
