package com.cwq.pingpong.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cwq.pingpong.domain.Message;
import com.cwq.pingpong.util.HttpUtil;
import com.cwq.pingpong.util.HttpUtil.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:webMvc-config.xml" })
public class HttpPushControllerTest {

	@Test
	public void testPushToUser() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", "111");
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("value1", "pushToUser");

		params.put("targetDevice", Message.ALL);
		params.put("data", datas);

		String url = "http://localhost:8080/api/push/user";
		Response response = HttpUtil.sendPostRequest(url, null, params);
		System.out.println(response);
	}

	@Test
	public void testBroadcast() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("value1", "broadcast");

		params.put("targetDevice", Message.ALL);
		params.put("data", datas);

		String url = "http://localhost:8080/api/push/broadcast/" + Message.ALL;
		Response response = HttpUtil.sendPostRequest(url, null, params);
		System.out.println(response);
	}
}
