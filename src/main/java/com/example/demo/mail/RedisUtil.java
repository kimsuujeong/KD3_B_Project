package com.example.demo.mail;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedisUtil {

	private final StringRedisTemplate redisTemplate;
	
	// key: 인증코드
	// Value : 이메일

	public String getData(String key) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		return valueOperations.get(key);
	}

	public void setDataExpire(String key, String value, long duration) {
		// duration 동안 (key, value)를 저장한다.
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		Duration expireDuration = Duration.ofMillis(duration);
		valueOperations.set(key, value, expireDuration);
	}

	public void deleteData(String key) {
		// 데이터 삭제
		redisTemplate.delete(key);
	}

}
