package com.goog.crowed.api;

import com.goog.crowed.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="rabbitmq-provider")
public interface RabbitProducerRemoteService {

    @RequestMapping("/rabbitmq/send/message")
    ResultEntity<String> sendMessage(@RequestParam("type") String type, @RequestParam("message") String message);
}
