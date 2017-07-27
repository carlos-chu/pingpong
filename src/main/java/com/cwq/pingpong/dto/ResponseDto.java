package com.cwq.pingpong.dto;

import java.io.Serializable;

import com.cwq.pingpong.enums.ExceptionEnum;
import com.cwq.pingpong.exceptions.PingpongException;

/**
 * 响应类.
 * 
 * @author bjchuwenqiang
 *
 * @param <T>
 */
public class ResponseDto<T> implements Serializable {
	private static final long serialVersionUID = -4893293015040919658L;
	private boolean ret;
	private T data;
	private Integer errCode;
	private String errMsg;

	public ResponseDto<T> err(ExceptionEnum exceptionEnum) {
		return err(exceptionEnum.getCode(), exceptionEnum.getMessage());
	}

	public ResponseDto<T> err(PingpongException exception) {
		return err(exception.getErrCode(), exception.getErrChineseMsg());
	}

	public ResponseDto<T> err(Throwable exception) {
		return err(ExceptionEnum.SYS_ERR);
	}

	private ResponseDto<T> err(Integer errCode, String errMsg) {
		setRet(false);
		setErrCode(errCode);
		setErrMsg(errMsg);
		return this;
	}

	public ResponseDto<T> succ(T data) {
		setRet(true);
		setData(data);
		return this;
	}

	public boolean isRet() {
		return ret;
	}

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		return "{\"ret\":\"" + ret + "\",\"data\":\"" + data + "\",\"errCode\":\"" + errCode + "\",\"errMsg\":\""
				+ errMsg + "\"}";
	}

}