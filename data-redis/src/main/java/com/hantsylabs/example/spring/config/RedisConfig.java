package com.hantsylabs.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("192.168.1.103");
		connectionFactory.setUsePool(true);
		return connectionFactory;
	}

	// @Bean
	// public RedisConnectionFactory jedisConnectionFactory() {
	// RedisSentinelConfiguration sentinelConfig = new
	// RedisSentinelConfiguration().master("mymaster")
	// .sentinel("127.0.0.1", 26379).sentinel("127.0.0.1", 26380);
	// return new JedisConnectionFactory(sentinelConfig);
	// }

}
