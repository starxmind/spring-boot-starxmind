package com.starxmind.boot.concurrent.lock.aspects;

import com.starxmind.bass.concurrent.lock.LeaseXLock;
import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.boot.concurrent.lock.XLockFactoryHolder;
import com.starxmind.boot.concurrent.lock.annotation.LeaseLock;
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
public class LeaseLockAspect extends AbstractAspect {
    private final XLockFactoryHolder xLockFactoryHolder;

    @Pointcut("@annotation(com.starxmind.boot.concurrent.lock.annotation.LeaseLock)")
    public void pointCut() {
        log.debug("<LockAspect.pointCut> Declare the pointcut...");
    }

    @Around("pointCut() && @annotation(leaseLock)")
    public Object execute(ProceedingJoinPoint joinPoint, LeaseLock leaseLock) throws Throwable {
        log.debug("<LeaseLockAspect.execute> LeaseLock begin...");

        String lockName = getLockName(joinPoint, leaseLock.value());
        XLockFactory xLockFactory = xLockFactoryHolder.get(leaseLock.clazz());
        LeaseXLock leaseXLock = (LeaseXLock) xLockFactory.get(lockName);

        Object proceed;
        try {
            leaseXLock.lock(leaseLock.leaseTime(), leaseLock.timeUnit());
            proceed = joinPoint.proceed();
        } finally {
            leaseXLock.unlock();
        }

        log.debug("<LeaseLockAspect.execute> LeaseLock end.");
        return proceed;
    }
}
