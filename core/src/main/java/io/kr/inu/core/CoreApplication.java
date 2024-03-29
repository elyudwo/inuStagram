package io.kr.inu.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.kr.inu.core", "io.kr.inu.infra"})
public class CoreApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-core,application-infra");
        SpringApplication.run(CoreApplication.class, args);
    }

}