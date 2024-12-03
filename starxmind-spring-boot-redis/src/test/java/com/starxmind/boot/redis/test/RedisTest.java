package com.starxmind.boot.redis.test;

import com.starxmind.boot.redis.RedisProperties;
import org.junit.Test;

public class RedisTest {

    @Test
    public void test() {
        RedisProperties redisProperties = new RedisProperties();
        System.out.println(redisProperties.getTimeout());
    }

}
