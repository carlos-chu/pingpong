package com.cwq.pingpong.constants;

/**
 * 接受客户端参数名常量
 * 
 * @author bjchuwenqiang
 * @date 2016年9月2日
 */
public class ReceiveConstants {

	public enum SendMode {
		TO_USER, TO_SERVER, TO_USER_AND_SERVER
	}

	/**
	 * 签名参数
	 */
	public static final String SIGN = "sign";

	/**
	 * 发送模式
	 */
	public static final String SEND_MODE = "sendMode";

	/**
	 * server的域名
	 */
	public static final String DOMAIN = "domain";

	/**
	 * to用户的Id
	 */
	public static final String TO_USER_ID = "toUserId";
	
	/**
	 * from用户的Id
	 */
	public static final String FROM_USER_ID = "fromUserId";

}
