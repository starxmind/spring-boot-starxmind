package com.starxmind.boot.snowflake;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 雪花ID池
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public class MemorySnowflakeIDPool extends SnowflakeIDPool {
    private BlockingQueue<Long> idPool;

    public MemorySnowflakeIDPool(int maximumPoolSize, int minimumIdle, SnowflakeIDGenerator snowflakeIDGenerator) {
        super(maximumPoolSize, minimumIdle, snowflakeIDGenerator);
    }

    @Override
    public void initQueue() {
        if (minimumIdle <= 0 || minimumIdle > maximumPoolSize) {
            throw new IllegalArgumentException("Invalid pool size configuration");
        }
        this.idPool = new LinkedBlockingQueue<>(maximumPoolSize);
    }

    @Override
    protected void checkPoolSize() {
        if (getPoolSize() < minimumIdle) {
            synchronized (this) {
                int numberOfIdsToGenerate = maximumPoolSize - getPoolSize(); // 补满池子
                while (numberOfIdsToGenerate > 0) {
                    long id = snowflakeIDGenerator.generate();
                    idPool.offer(id);
                    numberOfIdsToGenerate--;
                }
            }
            log.debug("The snowflake ID pool has been filled, now pool size: {}", getPoolSize());
        }
    }

    @Override
    public long take() throws InterruptedException {
        return idPool.take();
    }

    @Override
    public int getPoolSize() {
        return idPool.size();
    }
}
