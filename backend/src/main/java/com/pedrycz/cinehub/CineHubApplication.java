package com.pedrycz.cinehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CineHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CineHubApplication.class, args);
    }
}
