package com.cwq.pingpong.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 校验的注解
 * @author carlos.chu
 * @date 2015年8月17日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Validatable {

	// 字段描述
	String description() default "";

	// 是否可以为空
	boolean nullable() default false;

	// 是否是数字
	boolean isNumber() default false;

	// 是否是手机号码
	boolean isMobileNo() default false;

	// 映射的枚举
	Class<?> enumScope() default Void.class;

}