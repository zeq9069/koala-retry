package com.kyrincloud.koala_retry.spring;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.policy.RetryPolicyCache;
import com.kyrincloud.koala_retry.policy.SimpleRetryPolicy;
import com.kyrincloud.koala_retry.support.RetryContext;

@Aspect
public class SpringRetryAspectJ implements BeanFactoryAware{

	private BeanFactory factory;
	
	private RetryPolicyCache cache = new RetryPolicyCache();
	
	@Pointcut(value="@annotation(com.kyrincloud.koala_retry.annotation.Retry)")
	public void retryService(){}
	
	@Around(value = "retryService()")
	public Object around(ProceedingJoinPoint pj) throws Throwable{
		MethodSignature sig=(MethodSignature) pj.getSignature();
		Method method=sig.getMethod();
		Object [] args = pj.getArgs();
		SimpleRetryPolicy policy = getPolicy(method);
		RetryContext context =policy.buildContext(); 
		Throwable lastException = null;
		while(policy.canRetry(context)){
			try{
				lastException = null;
				return pj.proceed();
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
	
	private SimpleRetryPolicy getPolicy(Method method){
		SimpleRetryPolicy policy = cache.getPolicy(method);
		if(policy == null){
			Retry retry = method.getAnnotation(Retry.class);
			cache.addRetryPolicy(method, retry);
			policy = cache.getPolicy(method);
		}
		return policy;
	}

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.factory = factory;
	}

}
