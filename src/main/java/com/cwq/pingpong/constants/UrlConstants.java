package com.cwq.pingpong.constants;

public class UrlConstants {

	/**
	 * 向客户端定向发送消息地址
	 */
	public static final String URL_PUSH_TO_USER = "/queue/message/pushToClient/";
	
	/**
	 * 向客户端广播发送消息地址
	 */
	public static final String URL_PUSH_FOR_BROADCAST = "/topic/message/pushForBroadcast/";
}
