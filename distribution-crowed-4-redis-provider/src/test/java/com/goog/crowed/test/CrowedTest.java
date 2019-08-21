package com.goog.crowed.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrowedTest {

	@Autowired
	private RedisTemplate<Object, Object> template;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void testSaveValue() {
         ValueOperations<Object, Object> operator = template.opsForValue();
         
        // operator.set("keyone","valueone");
         System.out.println(operator.get("keyone"));
	}
	
	@Test
	public void testSaveValueByString() {
         ValueOperations<String, String> operator = stringRedisTemplate.opsForValue();
         
         operator.set("keyone","valueone");
         System.out.println(operator.get("keyone"));
	}
}
