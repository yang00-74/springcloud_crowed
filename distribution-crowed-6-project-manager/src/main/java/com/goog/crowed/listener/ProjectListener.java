package com.goog.crowed.listener;

import com.goog.crowed.utils.Constant;
import com.goog.crowed.utils.CrowdUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProjectListener {

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "RABBITMQ.YANG.PROJECT.QUEUE", durable = "true"),
                    exchange = @Exchange(value = "RABBITMQ.YANG.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
                    key = {Constant.MESSAGE_RABBITMQ_SAVE}
            ))
    public void save(String key) {
        if (CrowdUtils.isEmpty(key)) {
            System.out.println("Project listener:" + Constant.MESSAGE_RABBITMQ_MESSAGE_INVALID);
        }
        System.out.println("Project listener: save success" + key);
    }
}
