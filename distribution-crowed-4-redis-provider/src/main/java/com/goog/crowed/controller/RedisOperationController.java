package com.goog.crowed.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goog.crowed.entity.ResultEntity;
import com.goog.crowed.utils.Constant;
import com.goog.crowed.utils.CrowdUtils;

@RestController
public class RedisOperationController {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@RequestMapping("redis/save/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(@RequestParam("normalKey") String normalKey,
			@RequestParam("normalValue") String normalValue, @RequestParam("timeoutMinute") Integer timeoutMinute) {

		if (CrowdUtils.isEmpty(normalKey) || CrowdUtils.isEmpty(normalValue)) {
			return ResultEntity.failed(Constant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}

		if (null == timeoutMinute || timeoutMinute == 0) {
			return ResultEntity.failed(Constant.MESSAGE_REDIS_TIMEOUT_INVALID);
		}

		ValueOperations<String, String> operator = redisTemplate.opsForValue();
		if (timeoutMinute == -1) {
			// ≤ª…Ë÷√ time out ±£¥Ê
			try {
				operator.set(normalKey, normalValue);
			} catch (Exception e) {
				e.printStackTrace();
				return ResultEntity.failed(e.getMessage());
			}
			return ResultEntity.successNoData();
		}

		try {
			operator.set(normalKey, normalValue, timeoutMinute, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		return ResultEntity.successNoData();
	}

	@RequestMapping("redis/get/string/value/by/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey) {
		if (CrowdUtils.isEmpty(normalKey)) {
			return ResultEntity.failed(Constant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		try {
			String value = redisTemplate.opsForValue().get(normalKey);
			return ResultEntity.successWithData(value);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}

	}

	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key) {
		if (CrowdUtils.isEmpty(key)) {
			return ResultEntity.failed(Constant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		try {
			redisTemplate.delete(key);
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}

	}
}
