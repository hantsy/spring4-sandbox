package com.hantsylabs.example.spring.config;

import javax.servlet.Filter;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(0)
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
        		AppConfig.class,//
                JpaConfig.class, //          
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {
            WebConfig.class, //
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

	@Override
	protected Filter[] getServletFilters() {
		HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();

		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);

		return new Filter[] { httpMethodFilter, encodingFilter };
	}

}
