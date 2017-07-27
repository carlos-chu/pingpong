package com.cwq.pingpong.transfer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cwq.pingpong.util.HttpUtil;
import com.cwq.pingpong.util.HttpUtil.Response;

@Component
public class HttpTransferData implements TransferData<Response> {

	@Override
	public Response pull(String domain, Map<String, Object> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		return HttpUtil.sendGetRequest(domain, null, params);
	}

	@Override
	public Response push(String domain, Map<String, Object> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		return HttpUtil.sendPostRequest(domain, null, params);
	}
}
