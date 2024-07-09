package com.starxmind.boot.concurrent.lock.aspects;

import com.starxmind.bass.concurrent.lock.XLock;
import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.boot.concurrent.lock.XLockFactoryHolder;
import com.starxmind.boot.concurrent.lock.annotation.TryLock;
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
public class TryLockAspect extends AbstractAspect {
    private final XLockFactoryHolder xLockFactoryHolder;

    @Pointcut("@annotation(com.starxmind.boot.concurrent.lock.annotation.TryLock)")
    public void pointCut() {
        log.debug("<TryLockAspect.pointCut> Declare the pointcut...");
    }

    @Around("pointCut() && @annotation(tryLock)")
    public Object execute(ProceedingJoinPoint joinPoint, TryLock tryLock) throws Throwable {
        log.debug("<TryLockAspect.execute> TryLock begin...");

        String lockName = getLockName(joinPoint, tryLock.value());
        XLockFactory xLockFactory = xLockFactoryHolder.get(tryLock.clazz());
        XLock xLock = xLockFactory.get(lockName);
        boolean acquired;
        if (tryLock.waitTime() > 0) {
            acquired = xLock.tryLock(tryLock.waitTime(), tryLock.timeUnit());
        } else {
            acquired = xLock.tryLock();
        }
        Object proceed = null;
        if (acquired) {
            try {
                proceed = joinPoint.proceed();
            } finally {
                xLock.unlock();
            }
        }

        log.debug("<TryLockAspect.execute> TryLock end.");
        return proceed;
    }
}
