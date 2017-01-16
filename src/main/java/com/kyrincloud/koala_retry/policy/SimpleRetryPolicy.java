package com.kyrincloud.koala_retry.policy;

import com.kyrincloud.koala_retry.support.AbstractRetryContext;
import com.kyrincloud.koala_retry.support.RetryContext;

public class SimpleRetryPolicy implements RetryPolicy{
	
	private Sleep sleep;
	
	private int maxAttempt = 3;
	
	private int thisOneRetry = -1;
	
	private Class<? extends Throwable>[] includeExceptions;

	private Class<? extends Throwable>[] execludeExceptions;

	public boolean canRetry(RetryContext context) {
		SimlpeRetryContext contxt = (SimlpeRetryContext)context;
		return canRetry(context.getRetryCount(),contxt.getLastThrowable());
	}

	public void registThrowable(Throwable throwable) {
		registThrowable(throwable);
	}
	
	private boolean canRetry(int retryCount , Throwable lastExeception) {
		return (lastExeception == null || includeExeception(lastExeception)) && retryCount < maxAttempt;
	}
	
	private boolean includeExeception(Throwable lastExeception){
		if(includeExceptions.length == 0 && execludeExceptions.length == 0){
			return true;
		}
		
		if(execludeExceptions.length > 0){
			for(Class<?> clazz : execludeExceptions){
				if(clazz.isAssignableFrom(lastExeception.getClass())){
					return false;
				}
			}
		}
		
		if(includeExceptions.length == 0){ 
			return true;
		}else{
			for(Class<?> clazz : includeExceptions){
				if(clazz.isAssignableFrom(lastExeception.getClass())){
					return true;
				}
			}
			return false;
		}
		
	}
	
	public int getMaxAttempt() {
		return maxAttempt;
	}

	public Sleep getSleep(){
		return sleep;
	}

	public void setRetryInterval(long retryInterval) {
		this.sleep = new Sleep(retryInterval);
	}

	public void setMaxAttempt(int maxAttempt) {
		this.maxAttempt = maxAttempt;
	}

	public Class<? extends Throwable>[] getIncludeExceptions() {
		return includeExceptions;
	}

	public void setIncludeExceptions(Class<? extends Throwable>[] includeExceptions) {
		this.includeExceptions = includeExceptions;
	}

	public Class<? extends Throwable>[] getExecludeExceptions() {
		return execludeExceptions;
	}

	public void setExecludeExceptions(Class<? extends Throwable>[] execludeExceptions) {
		this.execludeExceptions = execludeExceptions;
	}
	
	public int getThisOneRetry() {
		return thisOneRetry;
	}

	public void setThisOneRetry(int thisOneRetry) {
		this.thisOneRetry = thisOneRetry;
	}

	public RetryContext buildContext(){
		return (RetryContext) new SimlpeRetryContext();
	}
	
	class SimlpeRetryContext extends AbstractRetryContext{
		public SimlpeRetryContext() {
			super();
		}
	}

}
