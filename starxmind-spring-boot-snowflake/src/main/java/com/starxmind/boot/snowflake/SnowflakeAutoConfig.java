package com.starxmind.boot.snowflake;

import com.starxmind.boot.snowflake.generator.SnowflakeIDGenerator;
import com.starxmind.boot.snowflake.pool.MemorySnowflakeIDPool;
import com.starxmind.boot.snowflake.pool.SnowflakeIDPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;

/**
 * 序列号生成器自动配置
 */
@Configuration
public class SnowflakeAutoConfig {
    /**
     * 注册雪花ID生成器
     *
     * @param workerId     机器标识符
     * @param dataCenterId 数据中心标识符
     * @return 雪花ID生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIDGenerator snowflakeIDGenerator(@Value("${starxmind.snowflake.worker.id:1}") Long workerId,
                                                     @Value("${starxmind.snowflake.datacenter.id:1}") Long dataCenterId) {
        SnowflakeIDGenerator snowflakeIDGenerator = new SnowflakeIDGenerator(workerId, dataCenterId);
        snowflakeIDGenerator.init();
        return snowflakeIDGenerator;
    }

    /**
     * 注册雪花ID池
     *
     * @param classname            雪花ID池的实现类
     * @param maximumPoolSize      池子容量
     * @param minimumIdle          最小空闲数量
     * @param snowflakeIDGenerator 雪花ID生成器
     * @return 雪花ID池
     * @throws Exception ex
     */
    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIDPool snowflakeIDPool(@Value("${starxmind.snowflake.pool.classname:}") String classname,
                                           @Value("${starxmind.snowflake.pool.maximumPoolSize:100}") int maximumPoolSize,
                                           @Value("${starxmind.snowflake.pool.minimumIdle:10}") int minimumIdle,
                                           SnowflakeIDGenerator snowflakeIDGenerator) throws Exception {
        // 传递雪花ID池的实现类名来指定雪花ID池
        if (StringUtils.isBlank(classname)) {
            classname = MemorySnowflakeIDPool.class.getName();
        }
        Class<?> childClass = Class.forName(classname);
        Constructor<?> constructor = childClass.getConstructor(int.class, int.class, SnowflakeIDGenerator.class);
        SnowflakeIDPool snowflakeIDPool = (SnowflakeIDPool) constructor.newInstance(maximumPoolSize, minimumIdle, snowflakeIDGenerator);
        snowflakeIDPool.init();
        return snowflakeIDPool;
    }
}
