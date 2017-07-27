package com.cwq.pingpong.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * WebSocket 配置文件
 * 
 * @author chuwenqiang
 *
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 注册WebSocket端点,客户端连接时需要此地址,并且增加SockJS支持,防止浏览器不支持websocket时出现的不兼容
	 */
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/connect").withSockJS();
	}

	/**
	 * 注册客户端输入通道
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
	}

	/**
	 * 注册客户端输出通道
	 */
	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(4).maxPoolSize(10);
	}

	/**
	 * 注册MessageBroker MessageBroker可实现WebSocket广播
	 * /queue是发送对个人的信息、/topic是发送广播、/app是浏览器请求地址前缀
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		logger.info("websocket server start...");
		registry.enableSimpleBroker("/queue/", "/topic/");
		registry.setApplicationDestinationPrefixes("/app");
	}

}
