package com.cwq.pingpong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

	@Value("${rabbitmq.host}")
	private String rabbitmqHost;

	public String getRabbitmqHost() {
		return rabbitmqHost;
	}

}
