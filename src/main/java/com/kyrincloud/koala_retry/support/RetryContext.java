package com.kyrincloud.koala_retry.support;

public interface RetryContext {
	
	public void registThrowable(Throwable throwable);
	
	public int getRetryCount();

}
