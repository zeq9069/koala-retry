package com.kyrincloud.koala_retry.interceptors;

import java.lang.reflect.Method;

import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.policy.SimpleRetryPolicy;
import com.kyrincloud.koala_retry.support.RetryContext;

import net.sf.cglib.proxy.MethodProxy;

public class RetryInterceptor {
	
	private Object obj;
	
	private Method method;
	
	private Object[] args;
	
	private MethodProxy proxy;
	
	private RetryContext context;
	
	
	public RetryInterceptor(Object obj, Method method, Object[] args, MethodProxy proxy,RetryContext context) {
		this.obj = obj;
		this.method = method;
		this.args = args;
		this.proxy = proxy;
		this.context = context;
	}
	
	public Object invoke() throws Throwable {
		Object result = null;
				if(method.isAnnotationPresent(Retry.class)){
					int count = 0;
					SimpleRetryPolicy policy = context.getPolicy(method); 
					Throwable lastExeception = null;
					try{
						count++;
						result = proxy.invokeSuper(obj, args);
					}catch(Throwable e){
						lastExeception = e;
					}
					while(policy.canRetry(count,lastExeception) && !policy.isLast(count,lastExeception)){
							try{
								policy.getSleep().sleep();
								result = proxy.invokeSuper(obj, args);
							}catch(Throwable e){
								lastExeception = e;
							}
						}
					if(policy.isLast(count,lastExeception)){
						policy.getSleep().sleep();
						result = proxy.invokeSuper(obj, args);
					}else if(lastExeception != null){
						throw lastExeception;
					}
				}else{
					result = proxy.invokeSuper(obj, args);
				}
		return result;
	}

}
