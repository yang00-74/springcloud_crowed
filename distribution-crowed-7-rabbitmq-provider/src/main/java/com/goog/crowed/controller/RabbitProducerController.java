package com.goog.crowed.controller;

import com.goog.crowed.entity.ResultEntity;
import com.goog.crowed.utils.Constant;
import com.goog.crowed.utils.CrowdUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitProducerController {

	/**
	 * RabbitMQ 消息队列使用前需保证对应的 exchange 存在，且 binging 了队列
	 * http://localhost:15672/
	 * */

	@Autowired
	private AmqpTemplate amqpTemplate;

	@RequestMapping("/rabbitmq/send/message")
	ResultEntity<String> sendMessage(@RequestParam("type") String type, @RequestParam("message") String message) {

		if (CrowdUtils.isEmpty(message) || CrowdUtils.isEmpty(type)) {
			return ResultEntity.failed(Constant.MESSAGE_RABBITMQ_MESSAGE_INVALID);
		}
		try {
			amqpTemplate.convertAndSend(Constant.MESSAGE_RABBITMQ + type, message);
			System.out.println("invoke sendMessage.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		return ResultEntity.successNoData();
	}
}
