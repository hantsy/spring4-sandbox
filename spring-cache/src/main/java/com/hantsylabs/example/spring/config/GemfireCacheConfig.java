package com.hantsylabs.example.spring.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.support.GemfireCacheManager;

import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.Region;
import com.hantsylabs.example.spring.model.Conference;

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
@Profile("gemfire")
public class GemfireCacheConfig extends CachingConfigurerSupport{
	
	@Bean
	CacheFactoryBean cacheFactoryBean() {
		return new CacheFactoryBean();
	}

	@Bean
	LocalRegionFactoryBean<String, Conference> localRegionFactory(final GemFireCache cache) {
		return new LocalRegionFactoryBean<String, Conference>() {{
			setCache(cache);
			setName("conference");
		}};
	}

	@Override
	@Bean
	public CacheManager cacheManager() {
		GemfireCacheManager _cacheManager= new GemfireCacheManager();
		try {
			_cacheManager.setCache(cacheFactoryBean().getObject());
			Set<Region<?,?>> regions=new HashSet<>();
			regions.add(localRegionFactory(cacheFactoryBean().getObject()).getObject());
			_cacheManager.setRegions(regions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return _cacheManager;
	}

	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

}
