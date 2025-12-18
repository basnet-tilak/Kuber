package com.kuber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kuber")
public class KuberApplication {
    public static void main(String[] args) {
        SpringApplication.run(KuberApplication.class, args);
    }
}