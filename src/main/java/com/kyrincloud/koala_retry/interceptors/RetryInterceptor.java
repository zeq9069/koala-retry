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
	
	private RetryPolicyCache cache = new RetryPolicyCache();
	
	
	public RetryInterceptor(Object obj, Method method, Object[] args, MethodProxy proxy) {
		this.obj = obj;
		this.method = method;
		this.args = args;
		this.proxy = proxy;
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
				if(policy.getThisOneRetry() != -1){
					Object obj = args[policy.getThisOneRetry()];
					if(obj instanceof Boolean){
						Boolean retry = (Boolean) obj;
						if(!retry){
							break; 
						}
					}
				}
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
