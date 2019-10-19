package com.goog.crowed.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goog.crowed.entity.ResultEntity;

@FeignClient(value = "redis-provider")
public interface RedisOperationRemoteService {

	/**
	 * Used to save <String, String> to redis, and set timeout minute
	 *  -1 �޹���ʱ�� +N ���ù���ʱ��N 0����null ��Чֵ 
	 **/
	@RequestMapping("/redis/save/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(@RequestParam("normalKey") String normalKey,
			@RequestParam("normalValue") String normalValue, @RequestParam("timeoutMinute") Integer timeoutMinute);

	/**
	 * Used to get value by key
	 * */
	@RequestMapping("/redis/get/string/value/by/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey);

	/**
	 * Used to remove value by key
	 * */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key);

}
