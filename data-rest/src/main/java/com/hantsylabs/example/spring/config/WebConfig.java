package com.hantsylabs.example.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig extends RepositoryRestMvcConfiguration{

}
