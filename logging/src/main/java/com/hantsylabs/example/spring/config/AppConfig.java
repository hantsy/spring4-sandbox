package com.hantsylabs.example.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(
        basePackages={"com.hantsylabs.example.spring"}
)
public class AppConfig {

}
