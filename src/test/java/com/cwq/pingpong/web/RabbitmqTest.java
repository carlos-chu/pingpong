package com.cwq.pingpong.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cwq.pingpong.mq.MessageConsumerService;
import com.cwq.pingpong.mq.MessageProductorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext*.xml", "classpath:webMvc-config.xml" })
public class RabbitmqTest {

	@Autowired
	private MessageProductorService messageProductor;
	@Autowired
	private MessageConsumerService messageConsumer;

	@Test
	public void testMessageQueueManager() {
//		messageProductor.pushMessage("rabbitQueue", "hello giraffe");
		try {
			messageConsumer.receiveAndConvert("test", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
