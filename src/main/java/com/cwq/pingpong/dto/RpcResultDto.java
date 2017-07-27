package com.cwq.pingpong.dto;

import java.io.Serializable;

/**
 * 响应基类，提供状态.
 *
 * @author chuwenqiang
 * @version $ v1.0 $
 */
public class RpcResultDto implements Serializable {

	private static final long serialVersionUID = 4862076246292487661L;

	public static final String ACCPET = "0";
	public static final String SUCCESS = "1";
	public static final String FAILURE = "2";

	private String status;
	private String errCode;
	private String errMsg;

	public RpcResultDto() {
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"status\":\"").append(status).append("{\"errCode\":\"").append(errCode)
				.append("{\"errMsg\":\"").append(errMsg).append("\"}");
		return builder.toString();
	}

	public boolean isSuccess() {
		return SUCCESS.equals(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
