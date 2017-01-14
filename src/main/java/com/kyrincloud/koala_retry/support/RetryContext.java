package com.kyrincloud.koala_retry.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.helper.ClassHelper;
import com.kyrincloud.koala_retry.policy.SimpleRetryPolicy;

public class RetryContext {

	private ClassHelper classHelper;
	
	private Map<String, SimpleRetryPolicy> retryPolicyCache = new HashMap<String, SimpleRetryPolicy>();
	
	public RetryContext(ClassHelper classHelper) {
		this.classHelper = classHelper;
		init();
	}
	
	@SuppressWarnings("static-access")
	public void init(){
		Set<Method> methods = classHelper.getMethodSetByAnnotation(Retry.class);
		for(Method m : methods){
			Retry retry = m.getAnnotation(Retry.class);
			SimpleRetryPolicy policy = new SimpleRetryPolicy();
			policy.setExceptions(retry.value());
			policy.setMaxAttempt(retry.maxAttempt());
			policy.setRetryInterval(retry.interval());
			retryPolicyCache.put(m.getName(), policy);
		}
	}
	
	public  void addRetryPolicy(Method method , Retry retry){
		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		Class<? extends Throwable>[] values = retry.value();
		policy.setExceptions(values);
		if(retry.maxAttempt() > 1){
			policy.setMaxAttempt(retry.maxAttempt());
		}
		if(retry.interval() >= 0){
			policy.setRetryInterval(retry.interval());
		}
		retryPolicyCache.put(method.getName(),policy);
	}
	
	public SimpleRetryPolicy getPolicy(Method method){
		return retryPolicyCache.get(method.getName());
	}
	
}
