package com.cwq.pingpong.enums;

import com.cwq.pingpong.exceptions.StatusCodes;

/**
 * 响应码
 * 
 * @author bjchuwenqiang
 */
public enum ExceptionEnum {
	SUCESS(StatusCodes.OK, "SUCCESS", "成功"),
	SYS_ERR(StatusCodes.INTERNAL_SERVER_ERROR, "SYS_ERR", "系统异常，请稍后重试"), 
	ACCEPTED(StatusCodes.ACCEPTED, "ACCEPTED", "已接受请求"), 
	CREATED(StatusCodes.CREATED, "CREATED", "已创建"), 
	BAD_REQUEST(StatusCodes.BAD_REQUEST, "BAD_REQUEST", "错误的请求"), 
	UNAUTHORIZED(StatusCodes.UNAUTHORIZED, "UNAUTHORIZED", "无权限访问"), 
	NOT_FOUND(StatusCodes.NOT_FOUND, "NOT_FOUND", "访问无该资源"), 
	SERVER_FAILURE(StatusCodes.SERVER_FAILURE, "SERVER_FAILURE", "服务不可用"), 
	RETRY_LATER(StatusCodes.RETRY_LATER, "RETRY_LATER", "请稍后重试"), 
	INVALID_PARAMETER(StatusCodes.INVALID_PARAMETER, "INVALID_PARAMETER", "参数有误"),
	ENUM_TRANSFER_ERR(StatusCodes.PARSER_ERROR, "ENUM_TRANSFER_ERR", "枚举值转换异常"),
	DATA_ISEMPTY_ERR(StatusCodes.INVALID_PARAMETER, "DATA_ISEMPTY_ERR", "源数据为空异常"),
	
	;

	private final Integer code;
	private final String message;
	private final String chineseMessage;

	private ExceptionEnum(Integer code, String desc, String chineseMessage) {
		this.code = code;
		this.message = desc;
		this.chineseMessage = chineseMessage;
	}

	/**
	 * Get the error code.
	 * 
	 * @return error code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * Description of the error.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Chinese description.
	 * 
	 * @return the chineseMessage
	 */
	public String getChineseMessage() {
		return chineseMessage;
	}
}
