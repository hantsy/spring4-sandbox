package com.hantsylabs.example.spring.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
@Profile("hz")
public class HazelcastCacheConfig implements CachingConfigurer {

	@Override
	@Bean
	public CacheManager cacheManager() {
		return new HazelcastCacheManager(hazelcastInstance());
	}

	@Bean
	public HazelcastInstance hazelcastInstance() {
		ClientNetworkConfig networkConfig = new ClientNetworkConfig()
				.addAddress("localhost");
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setNetworkConfig(networkConfig);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}

	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

}
