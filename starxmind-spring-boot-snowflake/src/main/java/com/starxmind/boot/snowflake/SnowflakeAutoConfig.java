package com.starxmind.boot.snowflake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 序列号生成器自动配置
 */
@Configuration
public class SnowflakeAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIDGenerator snowflakeIDGenerator(@Value("${starxmind.snowflake.worker.id:1}") Long workerId,
                                                     @Value("${starxmind.snowflake.datacenter.id:1}") Long dataCenterId) {
        SnowflakeIDGenerator snowflakeIDGenerator = new SnowflakeIDGenerator(workerId, dataCenterId);
        snowflakeIDGenerator.init();
        return snowflakeIDGenerator;
    }

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIDPool snowflakeIDPool(@Value("${starxmind.snowflake.pool.maximumPoolSize:100}") int maximumPoolSize,
                                           @Value("${starxmind.snowflake.pool.minimumIdle:10}") int minimumIdle,
                                           @Value("${starxmind.snowflake.pool.impl:}") String impl,
                                           SnowflakeIDGenerator snowflakeIDGenerator) {
        // 以后拓展方式：传递雪花ID池的实现类名来指定雪花ID池
//        if (StringUtils.isBlank(impl)) {
//            impl = MemorySnowflakeIDPool.class.getName();
//        }
        SnowflakeIDPool snowflakeIDPool = new MemorySnowflakeIDPool(maximumPoolSize, minimumIdle, snowflakeIDGenerator);
        snowflakeIDPool.init();
        return snowflakeIDPool;
    }
}
