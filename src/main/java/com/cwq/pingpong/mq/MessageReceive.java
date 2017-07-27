package com.cwq.pingpong.mq;

import org.springframework.amqp.core.Message;

import com.cwq.pingpong.mq.MessageConsumerService.MessageListener;

/**
 * 消息接受接口
 * 
 * @author bjchuwenqiang
 * @date 2016年9月18日
 */
public interface MessageReceive {

	public DeclaredNameEnum DEFAULT_DECLARENAME = DeclaredNameEnum.direct;
	
	public enum DeclaredNameEnum {
		direct, fanout, topic, headers;
	}

	public Message receiveByQueueName(String queueName);

	public String receiveAndConvert(String routingKey, DeclaredNameEnum declareEnum) throws Exception;

	public <T> void receiveAndConvert(String routingKey, Class<T> clazz, DeclaredNameEnum declareEnum,
			MessageListener<T> listener) throws Exception;

}
