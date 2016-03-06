package com.hantsylabs.example.spring.config;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(Ordered.LOWEST_PRECEDENCE)
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setLoadOnStartup(2);
	}

	@Override	
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class, //
				JpaConfig.class, //
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class, //
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);

		return new Filter[] { encodingFilter };
	}

}
