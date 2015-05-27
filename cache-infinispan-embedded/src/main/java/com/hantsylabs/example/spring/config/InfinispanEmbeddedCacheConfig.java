package com.hantsylabs.example.spring.config;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
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

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
public class InfinispanEmbeddedCacheConfig extends CachingConfigurerSupport {

	@Override
	@Bean
	public CacheManager cacheManager() {

		return new SpringEmbeddedCacheManager(
				new DefaultCacheManager(
					new ConfigurationBuilder()
						.eviction()
							.maxEntries(20000)
							.strategy(EvictionStrategy.LIRS)
						.expiration()
							.wakeUpInterval(5000L)
							.maxIdle(120000L)
			           .build()
					)
				);
	}

	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

}
