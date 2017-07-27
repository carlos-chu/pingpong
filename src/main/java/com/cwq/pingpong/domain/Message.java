package com.cwq.pingpong.domain;

import java.io.Serializable;

/**
 * 消息
 * 
 * @author bjchuwenqiang
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 6963973092780975186L;

	public enum MessageType {
		TEXT, BINARY
	}

	public enum DeviceType {
		ALL, ANDROID, IPHONE, IPAD, BROWSER;
	}

	public static final String ALL = DeviceType.ALL.name().toLowerCase();
	public static final String ANDROID = DeviceType.ANDROID.name().toLowerCase();
	public static final String IPHONE = DeviceType.IPHONE.name().toLowerCase();
	public static final String IPAD = DeviceType.IPAD.name().toLowerCase();
	public static final String BROWSER = DeviceType.BROWSER.name().toLowerCase();

	private String targetDevice;

	private Object data;

	public String getTargetDevice() {
		return targetDevice;
	}

	public void setTargetDevice(String targetDevice) {
		this.targetDevice = targetDevice;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
