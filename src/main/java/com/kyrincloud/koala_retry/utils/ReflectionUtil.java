package com.kyrincloud.koala_retry.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {

	private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

	public static Object newInstance(Class<?> clazz){
		Object instance;
		try{
			instance = clazz.newInstance();
		}catch(Exception e){
			log.error("new instance fail.");
			throw new RuntimeException(e);
		}
		return instance;
	}
	
	public static Object invoke(Object target , Method method , Object... args){
		Object result;
		method.setAccessible(true);
		try{
			result = method.invoke(target, args);
		}catch(Exception e){
			log.error("invoke method fail.");
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static void setFiled(Object target , Field filed , Object value){
		try{
			filed.setAccessible(true);
			filed.set(target, value);
		}catch(Exception e){
			log.error("field set value fail.");
			throw new RuntimeException(e);
		}
	}

}
