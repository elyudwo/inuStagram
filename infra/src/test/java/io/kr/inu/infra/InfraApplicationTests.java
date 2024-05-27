package io.kr.inu.infra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InfraApplicationTests {

    @BeforeAll
    static void setup() {
        System.setProperty("spring.config.name", "application-infra");
    }

    @Test
    void contextLoads() {
    }

}
