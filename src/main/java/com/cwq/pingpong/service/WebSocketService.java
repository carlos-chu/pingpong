package com.cwq.pingpong.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cwq.pingpong.constants.ReceiveConstants;
import com.cwq.pingpong.constants.ReceiveConstants.SendMode;
import com.cwq.pingpong.enums.ExceptionEnum;
import com.cwq.pingpong.exceptions.PingpongException;
import com.cwq.pingpong.util.ValidateUtil;

@Service
public class WebSocketService {

	/**
	 * 检查并返回发送模式
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public SendMode checkAndGetSendMode(Map<String, Object> params) throws UnsupportedEncodingException {
		String sendMode = (String) params.get(ReceiveConstants.SEND_MODE);
		String fromUserId = (String) params.get(ReceiveConstants.FROM_USER_ID);
		if (sendMode == null || fromUserId == null) {
			throw new PingpongException(ExceptionEnum.INVALID_PARAMETER, "sendMode or fromUserId can not be null!");
		}
		if (sendMode.equals(ReceiveConstants.SendMode.TO_SERVER.name())) {
			String domain = (String) params.get(ReceiveConstants.DOMAIN);
			if (domain == null || !ValidateUtil.isUrl(URLDecoder.decode(domain, "UTF-8"))) {
				throw new PingpongException(ExceptionEnum.INVALID_PARAMETER, "to server, the domain can not be null!");
			}
			return ReceiveConstants.SendMode.TO_SERVER;
		}
		if (sendMode.equals(ReceiveConstants.SendMode.TO_USER.name())) {
			String userId = ((String) params.get(ReceiveConstants.TO_USER_ID)).trim();
			if (userId == null) {
				throw new PingpongException(ExceptionEnum.INVALID_PARAMETER, "to user, the userId can not be null!");
			}
			return ReceiveConstants.SendMode.TO_USER;
		}
		if (sendMode.equals(ReceiveConstants.SendMode.TO_USER_AND_SERVER.name())) {
			String domain = (String) params.get(ReceiveConstants.DOMAIN);
			if (domain == null || !ValidateUtil.isUrl(domain)) {
				throw new PingpongException(ExceptionEnum.INVALID_PARAMETER,
						"to serverAndUser, the domain can not be null!");
			}
			String userId = ((String) params.get(ReceiveConstants.TO_USER_ID)).trim();
			if (userId == null) {
				throw new PingpongException(ExceptionEnum.INVALID_PARAMETER,
						"to serverAndUser, the userId can not be null!");
			}
			return ReceiveConstants.SendMode.TO_USER_AND_SERVER;
		}
		throw new PingpongException(ExceptionEnum.INVALID_PARAMETER, "no such sendMode!");
	}
}
