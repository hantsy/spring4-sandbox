package com.hantsylabs.example.spring.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import com.hantsylabs.example.spring.faces.ViewScope;

@Configuration
@ComponentScan(
	basePackages= {"com.hantsylabs.example.spring"},
    excludeFilters = {
        @Filter(
            type = FilterType.ANNOTATION,
            value = {
                RestController.class,
                ControllerAdvice.class,
                Configuration.class
            }
        )
    }
)
public class AppConfig {
	
    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("view", new ViewScope());

        CustomScopeConfigurer bean = new CustomScopeConfigurer();
        bean.setScopes(scopes);

        return bean;
    }
		
}
