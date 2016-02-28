package com.hantsylabs.example.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@Configuration
@ComponentScan(basePackages = { "com.hantsylabs.example.spring.repository" } )
@EnableMapRepositories(basePackages= {"com.hantsylabs.example.spring.repository"})
public class KeyValueConfig {

}
