package com.kuber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.kuber.repository")
@EnableTransactionManagement
public class KuberApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuberApplication.class, args);
    }
}