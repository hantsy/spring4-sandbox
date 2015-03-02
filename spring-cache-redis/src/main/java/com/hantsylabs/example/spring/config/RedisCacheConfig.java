package com.hantsylabs.example.spring.config;

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
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
@Profile("redis")
public class RedisCacheConfig extends CachingConfigurerSupport {

	@Override
	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
		return cacheManager;
	}

	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());

		return redisTemplate;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		// JedisPoolConfig poolConfig=new JedisPoolConfig();
		// poolConfig.set

		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("192.168.1.103");
		connectionFactory.setUsePool(true);
		return connectionFactory;
	}

	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

}
