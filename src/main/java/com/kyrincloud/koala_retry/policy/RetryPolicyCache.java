package com.kyrincloud.koala_retry.policy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.helper.ClassHelper;

public class RetryPolicyCache {

	private static Map<String, SimpleRetryPolicy> retryPolicyCache = new HashMap<String, SimpleRetryPolicy>();
	
	static{
		Set<Method> methods = ClassHelper.getMethodSetByAnnotation(Retry.class);
		for(Method m : methods){
			Retry retry = m.getAnnotation(Retry.class);
			SimpleRetryPolicy policy = new SimpleRetryPolicy();
			policy.setMaxAttempt(retry.maxAttempt());
			policy.setRetryInterval(retry.interval());
			policy.setIncludeExceptions(retry.include());
			policy.setExecludeExceptions(retry.execlude());
			retryPolicyCache.put(m.getName(), policy);
		}
	}
	
	public void addRetryPolicy(Method method , Retry retry){
		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		Class<? extends Throwable>[] include = retry.include();
		Class<? extends Throwable>[] execlude = retry.execlude();
		policy.setIncludeExceptions(include);
		policy.setExecludeExceptions(execlude);
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
