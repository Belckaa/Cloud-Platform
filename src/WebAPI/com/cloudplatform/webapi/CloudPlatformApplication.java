package com.cloudplatform.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cloudplatform")
public class CloudPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudPlatformApplication.class, args);
    }
}