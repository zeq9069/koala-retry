package com.kyrincloud.koala_retry.policy;

import com.kyrincloud.koala_retry.support.RetryContext;

public interface RetryPolicy {
	
	public boolean canRetry(RetryContext context);
	
	public void registThrowable(Throwable throwable);
	
	public RetryContext buildContext();
 
}
