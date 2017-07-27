package com.cwq.pingpong.exceptions;

import com.cwq.pingpong.enums.ExceptionEnum;

/**
 * 异常
 * 
 * @author bjchuwenqiang
 * @version 1.0
 * @date 2016年8月18日
 */
public class PingpongException extends RuntimeException {
	private static final long serialVersionUID = -5932545125527427642L;
	private Integer errCode;
	private String message;
	private String errChineseMsg;

	public PingpongException(ExceptionEnum errEnum) {
		this(errEnum.getCode(), errEnum.getMessage(), errEnum.getChineseMessage());
	}

	public PingpongException(ExceptionEnum errEnum, String message) {
		this(errEnum.getCode(), message, message);
	}

	private PingpongException(Integer errCode, String message, String chineseMsg) {
		super(message);
		this.errCode = errCode;
		this.message = message;
		this.errChineseMsg = chineseMsg;
	}

	public PingpongException(Integer errCode, String errChineseMsg) {
		super(errChineseMsg);
		this.errCode = errCode;
		this.errChineseMsg = errChineseMsg;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public String getMessage() {
		return message;
	}

	public String getErrChineseMsg() {
		return errChineseMsg;
	}

}
