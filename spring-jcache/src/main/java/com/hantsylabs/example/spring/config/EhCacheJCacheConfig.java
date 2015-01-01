package com.hantsylabs.example.spring.config;

import org.ehcache.jcache.JCacheCachingProvider;
import org.ehcache.jcache.JCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
public class EhCacheJCacheConfig extends CachingConfigurerSupport {

	@Override
	@Bean
	public CacheManager cacheManager() {
		JCacheCacheManager cacheManager = new JCacheCacheManager();
		cacheManager.setCacheManager(new JCacheManager(
				new JCacheCachingProvider(), ehcache(), null, null));
		return cacheManager;
	}

	private net.sf.ehcache.CacheManager ehcache() {
		return new net.sf.ehcache.CacheManager();
	}

	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

}
