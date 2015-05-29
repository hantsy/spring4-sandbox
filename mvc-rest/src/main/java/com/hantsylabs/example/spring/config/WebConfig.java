package com.hantsylabs.example.spring.config;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(
    basePackages= {"com.hantsylabs.example.spring"},
    useDefaultFilters = false,
    includeFilters = {
        @Filter(
            type = FilterType.ANNOTATION,
            value = {
                Controller.class,
                RestController.class,
                ControllerAdvice.class
            })
    }
)
public class WebConfig extends SpringDataWebConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Inject
    private ObjectMapper objectMapper;


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(false);
        configurer.favorPathExtension(false);
    }

}
