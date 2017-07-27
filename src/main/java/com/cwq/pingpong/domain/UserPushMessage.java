package com.cwq.pingpong.domain;

/**
 * 推送给某个用户
 * 
 * @author bjchuwenqiang
 */
public class UserPushMessage extends Message {

	private static final long serialVersionUID = -2477348606564215061L;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
