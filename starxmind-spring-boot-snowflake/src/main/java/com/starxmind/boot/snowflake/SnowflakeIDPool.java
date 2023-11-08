package com.starxmind.boot.snowflake;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 雪花ID池
 *
 * @author pizzalord
 * @since 1.0
 */
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
        // Fill the pool to reach the minimumIdle size
        while (idPool.size() < minimumIdle) {
            long id = snowflakeIDGenerator.generate();
            idPool.offer(id);
        }
    }

    public long getId() {
        try {
            return idPool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error getting ID from pool", e);
        }
    }

    public void returnId(long id) {
        idPool.offer(id);
    }

    public int getPoolSize() {
        return idPool.size();
    }
}
