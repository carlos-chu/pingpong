package com.cwq.pingpong.mq;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwq.pingpong.enums.ExceptionEnum;
import com.cwq.pingpong.exceptions.PingpongException;

@Service
public class MessageProductorService implements RabbitTemplate.ConfirmCallback {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RabbitTemplate amqpTemplate;
	
	public <T> void pushMessage(String routingKey, T message) {
		if (routingKey == null || routingKey.equals("")) {
			throw new PingpongException(ExceptionEnum.DATA_ISEMPTY_ERR, "消息routingKey不可为空");
		}
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		amqpTemplate.convertAndSend(routingKey, message, correlationData);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			logger.info("消息被成功接受,messageId:{}", correlationData.getId());
		} else {
			logger.error("消息未被成功接受,messageId:{}, cause:{}", correlationData.getId(), cause);
		}
	}
}
