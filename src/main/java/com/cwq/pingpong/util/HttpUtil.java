package com.cwq.pingpong.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http Utility for process remote http invocation.
 *
 * @author pierre
 * @version $ v1.0 Sep 5, 2014 $
 */
public class HttpUtil {
	public static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

	public static class Response {
		public static final int STATUS_LINE_SUC = 200;

		public int code;
		public String content;

		public boolean success() {
			return STATUS_LINE_SUC == code;
		}

		public String toString() {
			return "{\"code\":\"" + code + "\", \"content\":\"" + content + "\"}  ";
		}
	}

	public static Response sendPostRequest(String url, String charset, Map<String, Object> params) throws Exception {
		// 1. Create uri
		URI uri = java.net.URI.create(url);
		// 2. Create HttpUriRequest
		HttpPost request = new HttpPost(uri);
		// 3. Create HttpClient
		HttpClient httpClient = getHttpClientByProtocal(uri);

		// Assemble parameters
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (Iterator<Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> entry = it.next();
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
		}

		request.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));
		// 4. Parse response
		return retrieveResponse(httpClient, request);
	}

	public static Response sendGetRequest(String url, String charset, Map<String, Object> params) throws Exception {
		// 1. Create URI
		URI uri = java.net.URI.create(url);
		// 2. Create request mode
		HttpGet request = new HttpGet(uri);
		// 3. Create HttpClient
		HttpClient httpClient = getHttpClientByProtocal(uri);

		// Assemble parameters
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		for (Entry<String, Object> entry : params.entrySet()) {
			basicHttpParams.setParameter(entry.getKey(), entry.getValue());
		}
		request.setParams(basicHttpParams);

		// 4. Parse response
		return retrieveResponse(httpClient, request);
	}

	private static Response retrieveResponse(HttpClient httpClient, HttpUriRequest request) throws Exception {
		HttpResponse httpResponse = httpClient.execute(request);
		Response response = new Response();
		response.code = httpResponse.getStatusLine().getStatusCode();

		BufferedReader reader = null;
		StringBuilder rc = new StringBuilder();
		String charset = "utf-8";
		try {
			Header charsetHeader = httpResponse.getEntity().getContentType();
			if (charsetHeader != null) {
				String contentType = charsetHeader.getValue();
				String charsetReal = getCharset(contentType);
				if (charsetReal != null) {
					charset = charsetReal;
				}
			}
			InputStream is = httpResponse.getEntity().getContent();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String line = null;
			while ((line = reader.readLine()) != null) {
				rc.append(line);
			}
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		response.content = rc.toString();
		return response;
	}

	private static String getCharset(String contentType) {
		String charset = null;
		final String CharsetIden = "charset=";
		if (StringUtil.isEmptyNull(contentType) || contentType.toLowerCase().indexOf(CharsetIden) < 0) {
			return charset;
		}

		int startIndex = contentType.toLowerCase().indexOf(CharsetIden);
		int endIndex = contentType.toLowerCase().indexOf(";", startIndex);
		if (endIndex == -1) {
			endIndex = contentType.length();
		}
		charset = contentType.substring(startIndex + CharsetIden.length(), endIndex);
		return charset;
	}

	private static HttpClient getHttpClientByProtocal(URI uri) throws Exception {
		HttpClient httpClient = null;
		// determine the scheme
		String scheme = uri.getScheme();
		if (StringUtil.isEmpty(scheme) || "http".equals(scheme.toLowerCase())) {
			// plain http context
			httpClient = getHttpClient(false);
		} else {
			// need ssl context
			httpClient = getHttpClient(true);
		}
		return httpClient;
	}

	private static HttpClient getHttpClient(boolean enableSSL) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 45000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 45000);

		if (enableSSL) {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, new TrustManager[] { new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
				} }, new SecureRandom());
			} catch (Exception e) {
				throw e;
			}

			SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslContext,
					org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager clientConnectionManager = httpClient.getConnectionManager();
			SchemeRegistry schemeRegistry = clientConnectionManager.getSchemeRegistry();
			schemeRegistry.register(new Scheme("https", 443, sslSocketFactory));
		}
		return httpClient;
	}

	/**
	 * Upload static resource file to <i>qunar-static-res server</i>.
	 *
	 * @param url
	 *            destination url
	 * @param filename
	 *            absolute file path
	 * @param params
	 *            parameters
	 * @throws Exception
	 */
	public static Response uploadResource(String url, File file, Map<String, String> params) throws Exception {
		// 1. Create uri
		// Build queryString
		StringBuffer queryString = new StringBuffer(512);
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			queryString.append(entry.getKey());
			queryString.append("=");
			queryString.append(entry.getValue());
			queryString.append("&");
		}
		if (queryString.length() > 0) {
			queryString = queryString.deleteCharAt(queryString.length() - 1);
			url += "?" + queryString.toString();
		}

		URI uri = java.net.URI.create(url);

		// 2. Create HttpClient
		HttpClient httpClient = getHttpClientByProtocal(uri);
		HttpPost request = new HttpPost(uri);

		// 3. Create MultipartEntity
		MultipartEntity multipartEntity = new MultipartEntity();

		multipartEntity.addPart("file", new FileBody(file));
		request.setEntity(multipartEntity);

		// 4. Parse response
		return retrieveResponse(httpClient, request);
	}

	/**
	 * Retrieve the real ip address.
	 *
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return ip地址: 10.20.140.132
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("x-real-ip");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		LOG.debug("x-real-ip = {}", new Object[] { ip });

		// filter proxy ip
		String[] stemps = ip.split(",");
		if (stemps != null && stemps.length >= 1) {
			// firth ip is the real client ip
			ip = stemps[0];
		}
		LOG.debug("Filter proxy ip = {}", new Object[] { ip });

		ip = ip.trim();
		if (ip.length() > 23) {
			ip = ip.substring(0, 23);
		}

		return ip;
	}
}
