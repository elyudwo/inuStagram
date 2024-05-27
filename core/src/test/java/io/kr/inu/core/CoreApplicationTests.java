package io.kr.inu.core;

import io.kr.inu.infra.redis.RedisConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
class CoreApplicationTests {

    @BeforeAll
    static void setup() {
        System.setProperty("spring.config.name", "application-core,application-infra");
    }

    @Test
    void contextLoads() {
    }

}
