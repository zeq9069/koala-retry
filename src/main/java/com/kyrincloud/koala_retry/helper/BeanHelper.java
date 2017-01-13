package com.kyrincloud.koala_retry.helper;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.kyrincloud.koala_retry.utils.ReflectionUtil;

public class BeanHelper {
	
	private static final Map<Class<?>,Object> BEANS = Maps.newHashMap();
	
	public BeanHelper(String basePack) {
		init(basePack);
	}
	
	private void init(String basePack){
		ClassHelper helper = new ClassHelper(basePack);
		Set<Class<?>> beans = helper.getBeans();
		for(Class<?> clazz : beans){
			BEANS.put(clazz, ReflectionUtil.newInstance(clazz));
		}
	}
	
	public Object getBean(Class<?> clazz){
		return BEANS.get(clazz);
	}
	
	public Map<Class<?>,Object> getBeanMap(){
		return BEANS;
	}

}
