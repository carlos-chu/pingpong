package com.cwq.pingpong.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import com.cwq.pingpong.annotation.Validatable;

/**
 * @Description: copy bean util
 * @author carlos.chu
 * @date 2015年8月20日
 */
public class IBeanCopier {

	private static final String TOENUM = "toEnum";
	private static final Object INTEGER_CLASS = "class java.lang.Integer";
	private static final Object INT_CLASS = "int";

	/**
	 * 复制属性方法
	 * 
	 * @param source
	 * @param target
	 * @param isConvertEnum
	 *            是否转换枚举
	 * @throws Exception
	 */
	public static void copy(Object source, Object target, boolean isConvertEnum) throws Exception {
		BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), true);
		if (!isConvertEnum) {
			copier.copy(source, target, new BasicTypeConvertor()); // 1.复制属性值
		} else {
			copier.copy(source, target, new BasicTypeIngoreIntegerConvertor()); // 1.复制属性值
			converter(source, target); // 2.转换枚举值
		}
	}

	/**
	 * 自定义converter
	 * 
	 * @param source
	 * @param target
	 * @param isConvertEnum
	 *            是否转换枚举
	 * @param converter
	 */
	public static void copy(Object source, Object target, boolean isConvertEnum, Converter converter) {
		if (converter == null) {
			converter = new BasicTypeConvertor();
		}
		BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), true);
		copier.copy(source, target, converter);
	}

	/**
	 * 枚举值装换
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	private static void converter(Object source, Object target) throws Exception {
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			Validatable validatable = field.getAnnotation(Validatable.class);
			field.setAccessible(true);
			if (validatable != null && field.get(source) != null) {
				Class<?> enumScope = validatable.enumScope();
				field.setAccessible(true);
				if (enumScope != null && !enumScope.equals(Void.class)) {
					Method toEnumMethod = enumScope.getMethod(TOENUM, Integer.class);
					Object enumVal = toEnumMethod.invoke(enumScope, field.get(source));
					processSetValue(field, target, enumVal);
				} else if (field.getGenericType().toString().equals(INTEGER_CLASS)
						|| field.getGenericType().toString().equals(INT_CLASS)) {
					processSetValue(field, target, field.get(source));
				}
			}
		}
	}

	// 为转化的字段设值，处理特殊的继承关系
	private static void processSetValue(Field field, Object target, Object value) throws Exception {
		Field targetField = null;
		try {
			targetField = target.getClass().getDeclaredField(field.getName());
		} catch (NoSuchFieldException e) { // 查看父类是否有属性符合
			targetField = target.getClass().getSuperclass().getDeclaredField(field.getName());
		}
		targetField.setAccessible(true);
		targetField.set(target, value);
	}

	/**
	 * @desc 基本类型转换 cwq
	 * @data 2015年9月21日
	 */
	static class BasicTypeConvertor implements Converter {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Object convert(Object value, Class target, Object context) {
			if (value instanceof Long) {
				return (Long) value;
			}
			if (value instanceof Integer) {
				return (Integer) value;
			}
			if (value instanceof Double) {
				return (Double) value;
			}
			if (value instanceof Float) {
				return (Float) value;
			}
			if (target.isAssignableFrom(Date.class)) {
				return DateUtil.StringToDate(value.toString(), DateUtil.LONG_DATE_PATTERN_NO_SYMBOL);
			}
			return value;
		}
	}

	/**
	 * @desc 基本类型转换,去除Integer的转换 cwq
	 * @data 2015年9月21日
	 */
	static class BasicTypeIngoreIntegerConvertor implements Converter {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Object convert(Object value, Class target, Object context) {
			if (value instanceof Long) {
				return (Long) value;
			}
			if (value instanceof Integer) {
				return null;
			}
			if (target.isAssignableFrom(Date.class)) {
				return DateUtil.StringToDate(value.toString(), DateUtil.LONG_DATE_PATTERN_NO_SYMBOL);
			}
			return value;
		}
	}
}