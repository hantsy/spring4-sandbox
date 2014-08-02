package com.hantsylabs.example.spring.config;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.spring.provider.SpringRemoteCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
@Profile("infinispan-remote")
public class InfinispanRemoteCacheConfig implements CachingConfigurer {

	@Override
	@Bean
	public CacheManager cacheManager() {

		return new SpringRemoteCacheManager(
				new RemoteCacheManager(			
					new ConfigurationBuilder()
						.addServer()
						.host("localhost")
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
