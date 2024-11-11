package io.kr.inu.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
