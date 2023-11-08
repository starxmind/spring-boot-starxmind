package com.starxmind.boot.snowflake;

import lombok.RequiredArgsConstructor;

/**
 * 雪花算法序列生成器
 *
 * @author pizzalord
 * @since 1.0
 */
@RequiredArgsConstructor
public class SnowflakeIDGenerator {

    private final long workerId;
    private final long dataCenterId;

    private SnowflakeAlgorithm javaSnowflakeAlgorithm;

    public void init() {
        this.javaSnowflakeAlgorithm = new SnowflakeAlgorithm(workerId, dataCenterId);
    }

    public long generate() {
        return javaSnowflakeAlgorithm.nextId();
    }
}
