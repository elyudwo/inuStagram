package io.kr.inu.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebClientApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,application-core,application-infra");
        SpringApplication.run(WebClientApplication.class, args);
    }
}
