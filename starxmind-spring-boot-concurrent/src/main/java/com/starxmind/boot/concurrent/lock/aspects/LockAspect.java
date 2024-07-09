package com.starxmind.boot.concurrent.lock.aspects;

import com.starxmind.bass.concurrent.lock.XLock;
import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.boot.concurrent.lock.XLockFactoryHolder;
import com.starxmind.boot.concurrent.lock.annotation.Lock;
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
    private final XLockFactoryHolder xLockFactoryHolder;

    @Pointcut("@annotation(com.starxmind.boot.concurrent.lock.annotation.Lock)")
    public void pointCut() {
        log.debug("<LockAspect.pointCut> Declare the pointcut...");
    }

    @Around("pointCut() && @annotation(lock)")
    public Object execute(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        log.debug("<LeaseLockAspect.execute> LeaseLock begin...");

        String lockName = getLockName(joinPoint, lock.value());
        XLockFactory xLockFactory = xLockFactoryHolder.get(lock.clazz());
        XLock xLock = xLockFactory.get(lockName);

        Object proceed;
        try {
            xLock.lock(lock.leaseTime(), lock.timeUnit());
            proceed = joinPoint.proceed();
        } finally {
            xLock.unlock();
        }

        log.debug("<LeaseLockAspect.execute> LeaseLock end.");
        return proceed;
    }
}
