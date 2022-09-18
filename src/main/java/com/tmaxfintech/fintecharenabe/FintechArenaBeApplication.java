package com.tmaxfintech.fintecharenabe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class FintechArenaBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FintechArenaBeApplication.class, args);
    }
}
