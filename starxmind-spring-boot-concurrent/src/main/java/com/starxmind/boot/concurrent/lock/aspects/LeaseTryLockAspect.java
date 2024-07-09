package com.starxmind.boot.concurrent.lock.aspects;

import com.starxmind.bass.concurrent.lock.LeaseXLock;
import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.boot.concurrent.lock.XLockFactoryHolder;
import com.starxmind.boot.concurrent.lock.annotation.LeaseTryLock;
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
public class LeaseTryLockAspect extends AbstractAspect {
    private final XLockFactoryHolder xLockFactoryHolder;

    @Pointcut("@annotation(com.starxmind.boot.concurrent.lock.annotation.LeaseTryLock)")
    public void pointCut() {
        log.debug("<TryLockAspect.pointCut> Declare the pointcut...");
    }

    @Around("pointCut() && @annotation(leaseTryLock)")
    public Object execute(ProceedingJoinPoint joinPoint, LeaseTryLock leaseTryLock) throws Throwable {
        log.debug("<LeaseTryLockAspect.execute> LeaseTryLock begin...");

        String lockName = getLockName(joinPoint, leaseTryLock.value());
        XLockFactory xLockFactory = xLockFactoryHolder.get(leaseTryLock.clazz());
        LeaseXLock leaseXLock = (LeaseXLock) xLockFactory.get(lockName);
        boolean acquired;
        if (leaseTryLock.waitTime() > 0) {
            acquired = leaseXLock.tryLock(leaseTryLock.waitTime(), leaseTryLock.timeUnit());
        } else {
            acquired = leaseXLock.tryLock();
        }
        Object proceed = null;
        if (acquired) {
            try {
                proceed = joinPoint.proceed();
            } finally {
                leaseXLock.unlock();
            }
        }

        log.debug("<LeaseTryLockAspect.execute> LeaseTryLock end.");
        return proceed;
    }
}
