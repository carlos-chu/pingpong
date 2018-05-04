/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cwq.pingpong.web;

import java.net.URLDecoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwq.pingpong.constants.ReceiveConstants;
import com.cwq.pingpong.constants.UrlConstants;
import com.cwq.pingpong.constants.ReceiveConstants.SendMode;
import com.cwq.pingpong.domain.Message;
import com.cwq.pingpong.dto.ResponseDto;
import com.cwq.pingpong.enums.ExceptionEnum;
import com.cwq.pingpong.exceptions.PingpongException;
import com.cwq.pingpong.service.WebSocketService;
import com.cwq.pingpong.transfer.HttpTransferEngine;
import com.cwq.pingpong.util.ValidateUtil;
import com.cwq.pingpong.util.HttpUtil.Response;

/**
 * Controller
 * 
 * @author chuwenqiang
 */
@Controller
public class WebSocketController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

	@Autowired
	private HttpTransferEngine httpTransEngine;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private WebSocketService webSocketService;

	/**
	 * <p>
	 * 客户端订阅的地址,客户端需要加上/app前缀
	 * </p>
	 * 
	 * @param deviceType
	 *            {@link Message.DeviceType}
	 */
	@SubscribeMapping("/message/subscribe/{domain}")
	public ResponseDto<String> subscribe(@DestinationVariable("domain") String domain) throws Exception {
		logger.debug("subscribe message, domain:{}", domain);
		return execute(domain, new BizCallback<String, String>() {
			@Override
			public String doInTransaction(String request) throws Exception {
				request = URLDecoder.decode(request, "UTF-8");
				if (!ValidateUtil.isUrl(request)) {
					throw new PingpongException(ExceptionEnum.INVALID_PARAMETER, "domain is not url");
				}
				Response response = httpTransEngine.pull(request, null);
				return response != null ? response.content : null;
			}
		});
	}

	/**
	 * 服务端接受客户端消息
	 * 
	 * @param params
	 */
	@ResponseBody
	@MessageMapping("/message/send")
	public ResponseDto<Void> receive(Map<String, Object> params) {
		logger.info("receive msg, params:{}", params);
		return execute(params, new BizCallbackWithoutResult<Map<String, Object>, Void>() {
			@Override
			protected void doInTransactionWithoutResult(Map<String, Object> request) throws Exception {
				SendMode sendMode = webSocketService.checkAndGetSendMode(request);
				processMessage(sendMode, request);
			}
		});
	}

	protected void processMessage(SendMode sendMode, Map<String, Object> params) throws Exception {
		String toUserId = (String) params.get(ReceiveConstants.TO_USER_ID);
		String domain = URLDecoder.decode((String) params.get(ReceiveConstants.DOMAIN), "UTF-8");
		String dest = UrlConstants.URL_PUSH_TO_USER + toUserId;
		switch (sendMode) {
		case TO_USER:
			messagingTemplate.convertAndSend(dest, params);
			break;
		case TO_SERVER:
			httpTransEngine.push(domain, params);
			break;
		case TO_USER_AND_SERVER:
			messagingTemplate.convertAndSend(dest, params);
			httpTransEngine.push(domain, params);
			break;
		default:
			break;
		}
	}

	/**
	 * 队列异常处理
	 * 
	 * @param exception
	 * @return
	 */
	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
		logger.error("process fail!,cause by", exception);
		return exception.getMessage();
	}

}
