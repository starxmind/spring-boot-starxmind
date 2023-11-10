package com.starxmind.boot.snowflake.pool;

import com.starxmind.boot.snowflake.generator.SnowflakeIDGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class SnowflakeIDPool {
    protected final int maximumPoolSize;
    protected final int minimumIdle;
    protected final SnowflakeIDGenerator snowflakeIDGenerator;

    public void init() {
        initQueue();
        checkPoolSize();
    }

    protected abstract void initQueue();

    protected abstract void checkPoolSize();

    public long getId(){
        checkPoolSize();
        try {
            return take();
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error getting ID from pool", ex);
        }
    }

    protected abstract long take() throws Exception;

    public abstract int getPoolSize();
}
