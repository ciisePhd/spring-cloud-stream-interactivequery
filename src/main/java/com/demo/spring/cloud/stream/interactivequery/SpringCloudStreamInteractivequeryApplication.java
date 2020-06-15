package com.demo.spring.cloud.stream.interactivequery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(BookTrackerProperties.class)
@SpringBootApplication
public class SpringCloudStreamInteractivequeryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamInteractivequeryApplication.class, args);
    }

}
