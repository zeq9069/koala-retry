package com.kyrincloud.koala_retry.support;

public class AbstractRetryContext implements RetryContext{

	private int count;
	
	private Throwable lastThrowable;
	
	public AbstractRetryContext() {
	}
	
	public void registThrowable(Throwable throwable) {
		lastThrowable = throwable;
		if(lastThrowable != null)
			count++;
	}

	public int getRetryCount() {
		return count;
	}
	
	public Throwable getLastThrowable(){
		return lastThrowable;
	}

}
