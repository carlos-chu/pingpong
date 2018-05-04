package com.cwq.pingpong.transfer;

import java.util.Map;

/**
 * 从第三方拉数据接口
 * 
 * @author bjchuwenqiang
 * @date 2016年9月1日
 */
public interface TransferEngine<T> {

	/**
	 * 拉数据接口，后期加上重试次数
	 * 
	 * @param domain
	 * @param params
	 * @return
	 * @throws Exception
	 */
	T pull(String domain, Map<String, Object> params) throws Exception;

	/**
	 * 推送数据接口,后期加上重试次数和异步功能
	 * 
	 * @param domain
	 * @param params
	 * @return
	 * @throws Exception
	 */
	T push(String domain, Map<String, Object> params) throws Exception;
}
