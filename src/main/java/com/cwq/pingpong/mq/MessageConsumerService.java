package com.cwq.pingpong.mq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwq.pingpong.config.Config;
import com.cwq.pingpong.enums.ExceptionEnum;
import com.cwq.pingpong.exceptions.PingpongException;
import com.cwq.pingpong.util.JsonUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Service
public class MessageConsumerService implements MessageReceive {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RabbitTemplate amqpTemplate;
	@Autowired
	private Queue rabbitQueue;
	@Autowired
	private Config config;

	public final static String EXCHANGE_NAME = "tranfer";

	public interface MessageListener<T> {
		void handleMessage(T t);
	}

	public class DefaultMessageListener implements MessageListener<Object> {
		@Override
		public void handleMessage(Object t) {
			logger.info("message received: {}", t);
		}
	}

	private <R> Message popMessage(String queueName) {
		if (queueName == null || queueName.equals("")) {
			throw new PingpongException(ExceptionEnum.DATA_ISEMPTY_ERR, "消息queueName不可为空");
		}
		Message message = amqpTemplate.receive(rabbitQueue.getName());
		amqpTemplate.receiveAndReply(queueName, new ReceiveAndReplyCallback<R, Boolean>() {
			@Override
			public Boolean handle(R payload) {
				logger.info("success receive:{}", payload);
				return true;
			}
		});
		logger.info("mq receive message:{}", new String(message.getBody()));
		return message;
	}

	@Override
	public Message receiveByQueueName(String queueName) {
		return popMessage(queueName);
	}

	@Override
	public String receiveAndConvert(String routingKey, DeclaredNameEnum declareEnum) throws Exception {
		if (declareEnum == null) {
			declareEnum = DEFAULT_DECLARENAME;
		}
		final StringBuffer sb = new StringBuffer();
		receiveAndConvert(routingKey, String.class, declareEnum, new MessageListener<String>() {
			@Override
			public void handleMessage(String t) {
				sb.append(t);
			}
		});
		return sb.toString();
	}

	@Override
	public <T> void receiveAndConvert(String routingKey, final Class<T> clazz, DeclaredNameEnum declareEnum,
			final MessageListener<T> listener) throws Exception {
		if (declareEnum == null) {
			declareEnum = DEFAULT_DECLARENAME;
		}
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(config.getRabbitmqHost());
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, DeclaredNameEnum.direct.name());
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
		channel.basicConsume(queueName, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				T bean = JsonUtil.toBean(message, clazz);
				listener.handleMessage(bean);
			}
		});
	}

}