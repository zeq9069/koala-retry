package com.kyrincloud.koala_retry.interceptors;

import java.lang.reflect.Method;

import com.kyrincloud.koala_retry.support.RetryContext;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RetryMethodInvocationBuilder {

	@SuppressWarnings("unchecked")
	public static  <T> T buildRetryInterceptor(final Class<?> target , final RetryContext context){
		return (T) Enhancer.create(target, new MethodInterceptor(){
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return new RetryInterceptor(obj, method, args, proxy,context).invoke();
			}});
	}
}
