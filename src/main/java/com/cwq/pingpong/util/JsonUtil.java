package com.cwq.pingpong.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.StringUtils;

public class JsonUtil {
	private static final Log logger = LogFactory.getLog(JsonUtil.class);
	private static ObjectMapper mapper = null;

	private static ObjectMapper getObjectMapper() {
		if (mapper == null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
			mapper = new ObjectMapper();
			// json串和对象字段不对应时候,只解析有对应的映射
			mapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.getSerializationConfig().setDateFormat(df);
			mapper.getDeserializationConfig().setDateFormat(df);
		}
		return mapper;
	}

	/**
	 * From json string to bean.
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(String json, Class<T> clazz) {
		if (!StringUtils.isEmpty(json)) {
			ObjectMapper mapper = getObjectMapper();
			try {
				return mapper.readValue(json, clazz);
			} catch (Exception e) {
				logger.error("JSONString : " + json, e);
			}
		}
		return null;
	}

	/**
	 * To json string.
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		ObjectMapper mapper = getObjectMapper();
		return toString(object, mapper);
	}

	/**
	 * To json string base on given mapper.
	 * 
	 * @param object
	 * @param mapper
	 * @return
	 */
	public static String toString(Object object, ObjectMapper mapper) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	/**
	 * to bean list
	 * 
	 * @param json
	 * @param collectionClass
	 *            the list subclass
	 * @param elementClass
	 *            the bean class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> toBeanList(String json, Class<? extends List> collectionClass, Class<T> elementClass) {
		ObjectMapper mapper = getObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClass);

		try {
			return (List<T>) mapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

}
