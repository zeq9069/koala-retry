package com.kyrincloud.koala_retry.helper;

import java.util.Map;

import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.interceptors.RetryMethodInvocationBuilder;

public class InterceptorHelper {
	
	static{
		Map<Class<?>,Object> objs = BeanHelper.getBeanMap();
		for(Map.Entry<Class<?>,Object> entry : objs.entrySet()){
			ClassHelper.getMethodSetByAnnotation(Retry.class);
			Object obj = RetryMethodInvocationBuilder.buildRetryInterceptor(entry.getKey());
			objs.put(entry.getKey(), obj);
		}
	}
	
}
