package com.kyrincloud.koala_retry.helper;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.kyrincloud.koala_retry.utils.ReflectionUtil;

public class BeanHelper {
	
	private static final Map<Class<?>,Object> BEANS = Maps.newHashMap();
	
	private ClassHelper helper;
	
	public BeanHelper(String basePack) {
		 this.helper = new ClassHelper(basePack);
		init(basePack);
	}
	
	private void init(String basePack){
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
	
	public ClassHelper getClassHelper(){
		return helper;
	}

}
