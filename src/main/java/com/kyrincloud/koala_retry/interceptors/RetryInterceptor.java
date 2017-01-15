package com.kyrincloud.koala_retry.interceptors;

import java.lang.reflect.Method;

import com.kyrincloud.koala_retry.policy.RetryPolicyCache;
import com.kyrincloud.koala_retry.policy.SimpleRetryPolicy;
import com.kyrincloud.koala_retry.support.RetryContext;

import net.sf.cglib.proxy.MethodProxy;

public class RetryInterceptor {
	
	private Object obj;
	
	private Method method;
	
	private Object[] args;
	
	private MethodProxy proxy;
	
	private RetryPolicyCache cache;
	
	
	public RetryInterceptor(Object obj, Method method, Object[] args, MethodProxy proxy,RetryPolicyCache cache) {
		this.obj = obj;
		this.method = method;
		this.args = args;
		this.proxy = proxy;
		this.cache = cache;
	}
	
	public Object invoke() throws Throwable {
		SimpleRetryPolicy policy = cache.getPolicy(method);
		RetryContext context =policy.buildContext(); 
		Throwable lastException = null;
		while(policy.canRetry(context)){
			try{
				lastException = null;
				return proxy.invokeSuper(obj, args);
			}catch(Throwable e){
				lastException = e;
				context.registThrowable(lastException);
				if(policy.canRetry(context)){
					policy.getSleep().sleep();
				}
			}
		}
		if(lastException != null){
			throw lastException;
		}
		return null;
	}

}
