package com.kyrincloud.koala_retry.helper;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.kyrincloud.koala_retry.utils.ReflectionUtil;

public class BeanHelper {
	
	private static final Map<Class<?>,Object> BEANS = Maps.newHashMap();
	
	
	static{
		Set<Class<?>> beans = ClassHelper.getBeans();
		for(Class<?> clazz : beans){
			BEANS.put(clazz, ReflectionUtil.newInstance(clazz));
		}
	}
	
	public static Object getBean(Class<?> clazz){
		return BEANS.get(clazz);
	}
	
	public static Map<Class<?>,Object> getBeanMap(){
		return BEANS;
	}

}
