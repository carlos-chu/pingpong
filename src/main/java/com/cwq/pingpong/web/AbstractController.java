package com.cwq.pingpong.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwq.pingpong.dto.ResponseDto;
import com.cwq.pingpong.exceptions.PingpongException;
import com.cwq.pingpong.util.BaseInfoValidater;

public abstract class AbstractController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 具体业务策略
	 */
	protected interface BizCallback<R, T> {
		T doInTransaction(R request) throws Exception;
	}

	protected abstract class BizCallbackWithoutResult<R, T> implements BizCallback<R, T> {
		public final T doInTransaction(R request) throws Exception {
			doInTransactionWithoutResult(request);
			return null;
		}

		protected abstract void doInTransactionWithoutResult(R request) throws Exception;
	}

	/**
	 * 业务入口
	 * 
	 * @param request
	 *            请求参数
	 * @param callback
	 *            回调接口
	 * @return
	 */
	protected <R, T> ResponseDto<T> execute(R request, BizCallback<R, T> callback) {
		logger.info("request params:{}", request);
		ResponseDto<T> result = new ResponseDto<T>();
		try {
			if (request == null) {
				logger.warn("warning!!!, request is null");
			}
			BaseInfoValidater.checkParam(request);

			T data = callback.doInTransaction(request);

			result.succ(data);
		} catch (PingpongException we) {
			logger.error("websocket biz has error, cause by", we);
			result.err(we);
		} catch (Throwable e) {
			logger.error("websocket sys has error, cause by", e);
			result.err(e);
		}
		return result;
	}

}