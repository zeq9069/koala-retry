package com.kyrincloud.koala_retry.interceptors;

import java.lang.reflect.Method;

import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.annotation.Service;
import com.kyrincloud.koala_retry.policy.RetryPolicyCache;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RetryMethodInvocationBuilder {
	@SuppressWarnings("unchecked")
	public static  <T> T buildRetryInterceptor(final Class<?> target){
		return (T) Enhancer.create(target, new MethodInterceptor(){
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				RetryPolicyCache context = new RetryPolicyCache();
				if(target.getAnnotation(Service.class) != null || target.getAnnotation(org.springframework.stereotype.Service.class) != null){
					if(method.getAnnotation(Retry.class) != null){
						return new RetryInterceptor(obj, method, args, proxy , context).invoke();
					}
				}
				return proxy.invokeSuper(obj, args);
			}});
	}
}
