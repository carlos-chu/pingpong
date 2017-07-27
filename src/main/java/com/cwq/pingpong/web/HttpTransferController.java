package com.cwq.pingpong.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwq.pingpong.constants.UrlConstants;
import com.cwq.pingpong.domain.BroadcastPushMessage;
import com.cwq.pingpong.domain.UserPushMessage;
import com.cwq.pingpong.dto.ResponseDto;

/**
 * http请求转发消息给客户端控制器
 * 
 * @author bjchuwenqiang
 */
@Controller
@RequestMapping("/api/push")
public class HttpTransferController extends AbstractController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/**
	 * 接受外部的数据向客户端发送消息，必要时加上队列消息
	 * 
	 * @param userPushMsg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseDto<Void> pushToUser(UserPushMessage userPushMsg) {
		checkRequest(userPushMsg);
		return execute(userPushMsg, new BizCallbackWithoutResult<UserPushMessage, Void>() {
			@Override
			protected void doInTransactionWithoutResult(UserPushMessage request) {
				String dest = UrlConstants.URL_PUSH_TO_USER + request.getUserId();
				messagingTemplate.convertAndSend(dest, request.getData());
			}
		});
	}

	private void checkRequest(UserPushMessage userPushMsg) {
		// TODO:
	}

	/**
	 * 接受外部的数据向客户端发送消息，必要时加上队列消息
	 * 
	 * @param device
	 * @param broadcastMessage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/broadcast/{device}", method = RequestMethod.POST)
	public ResponseDto<Void> broadcast(@PathVariable final String device, BroadcastPushMessage broadcastMessage) {
		checkRequest(broadcastMessage);
		return execute(broadcastMessage, new BizCallbackWithoutResult<BroadcastPushMessage, Void>() {
			@Override
			protected void doInTransactionWithoutResult(BroadcastPushMessage request) {
				String dest = UrlConstants.URL_PUSH_FOR_BROADCAST + device;
				messagingTemplate.convertAndSend(dest, request.getData());
			}
		});
	}

	private void checkRequest(BroadcastPushMessage broadcastMessage) {
		// TODO:
	}

}
