package com.kyrincloud.koala_retry.policy;

public class SimpleRetryPolicy implements RetryPolicy{
	
	private Sleep sleep;
	
	private int maxAttempt = 3;
	
	private Class<? extends Throwable>[] exceptions;

	public boolean canRetry(int retryCount , Throwable lastExeception) {
		if(retryCount < maxAttempt-1){
			if(exceptions == null){
				return true;
			}
			if(lastExeception == null){
				return false;
			}
			for(Class<? extends Throwable> clazz : exceptions){
				if(clazz.isAssignableFrom(lastExeception.getClass())){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isLast(int retryCount){
		return maxAttempt-1 == retryCount;
	}

	public int getMaxAttempt() {
		return maxAttempt;
	}

	public Class<? extends Throwable>[] getExceptions() {
		return exceptions;
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

	public void setExceptions(Class<? extends Throwable>[] exceptions) {
		this.exceptions = exceptions;
	}
	

}
