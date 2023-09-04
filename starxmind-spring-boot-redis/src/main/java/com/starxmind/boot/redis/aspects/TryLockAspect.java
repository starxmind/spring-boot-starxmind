package com.starxmind.boot.redis.aspects;

import com.starxmind.boot.redis.annotation.TryLock;
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
public class TryLockAspect extends AbstractAspect {
    private final DistributedLockFactory distributedLockFactory;

    @Pointcut("@annotation(com.starxmind.boot.redis.annotation.TryLock)")
    public void pointCut() {
        log.debug("<TryLockAspect.pointCut> Declare the pointcut...");
    }

    @Around("pointCut() && @annotation(tryLock)")
    public Object execute(ProceedingJoinPoint joinPoint, TryLock tryLock) throws Throwable {
        log.debug("<TryLockAspect.execute> TryLock begin...");

        String lockName = getLockName(joinPoint, tryLock.value());
        DistributedLock distributedLock = distributedLockFactory.get(lockName);
        boolean acquired = distributedLock.tryLock(tryLock.waitTime(), tryLock.leaseTime(), tryLock.unit());

        Object proceed = null;
        if (acquired) {
            try {
                proceed = joinPoint.proceed();
            } catch (Throwable throwable) {
                throw throwable;
            } finally {
                distributedLock.unlock();
            }
        }

        log.debug("<TryLockAspect.execute> TryLock end.");
        return proceed;
    }
}
