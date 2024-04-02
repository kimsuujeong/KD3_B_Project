package com.example.demo.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedisUtil {

	@Autowired
	private final RedisTemplate<Integer, String> redisTemplate;
	
	// key: 인증코드
	// Value : 이메일

    public String getData(Integer key) {
        ValueOperations<Integer ,String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(Integer key, String value) {
        ValueOperations<Integer ,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDataExpire(Integer key, String value, long duration) {
    	try {
    		
            ValueOperations<Integer, String> valueOperations = redisTemplate.opsForValue();
            Duration expireDuration = Duration.ofSeconds(duration);
            valueOperations.set(key, value, expireDuration);
            
            System.out.println("데이터를 Redis에 성공적으로 저장했습니다.");
            
            System.out.println("키값test: "+ getData(key)); // 키값 test (value = mail값 성공적으로 가져옴)
            
        } catch (Exception e) {
        	
            System.out.println("Redis에 데이터를 저장하는 동안 오류가 발생했습니다: " + e.getMessage());
            
        }
    }

    public void deleteData(Integer key) {
    	
        redisTemplate.delete(key);
        
    }

}
