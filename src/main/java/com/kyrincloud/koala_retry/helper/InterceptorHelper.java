package com.kyrincloud.koala_retry.helper;

import java.util.Map;

import com.kyrincloud.koala_retry.interceptors.RetryMethodInvocationBuilder;
import com.kyrincloud.koala_retry.support.RetryContext;

public class InterceptorHelper {
	
	private BeanHelper beanHelper;
	
	private RetryContext context;
	
	public InterceptorHelper(BeanHelper beanHelper , RetryContext context) {
		this.beanHelper = beanHelper;
		this.context = context;
		init();
	}
	
	private void init(){
		Map<Class<?>,Object> objs = beanHelper.getBeanMap();
		for(Map.Entry<Class<?>,Object> entry : objs.entrySet()){
			Object obj = RetryMethodInvocationBuilder.buildRetryInterceptor(entry.getKey(),context);
			objs.put(entry.getKey(), obj);
		}
		
	}

}
