package com.starxmind.boot.snowflake;

import lombok.Data;
import lombok.RequiredArgsConstructor;
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
@Data
@RequiredArgsConstructor
public class SnowflakeIDPool {
    private final int maximumPoolSize;
    private final int minimumIdle;
    private final SnowflakeIDGenerator snowflakeIDGenerator;
    private final BlockingQueue<Long> idPool;

    public SnowflakeIDPool(int maximumPoolSize, int minimumIdle, SnowflakeIDGenerator snowflakeIDGenerator) {
        if (minimumIdle <= 0 || minimumIdle > maximumPoolSize) {
            throw new IllegalArgumentException("Invalid pool size configuration");
        }

        this.maximumPoolSize = maximumPoolSize;
        this.minimumIdle = minimumIdle;
        this.snowflakeIDGenerator = snowflakeIDGenerator;
        this.idPool = new LinkedBlockingQueue<>(maximumPoolSize);
    }

    public long getId() {
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

        try {
            return idPool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error getting ID from pool", e);
        }
    }

    public int getPoolSize() {
        return idPool.size();
    }
}
