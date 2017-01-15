package com.kyrincloud.koala_retry.policy;

public class SimpleRetryPolicy implements RetryPolicy{
	
	private Sleep sleep;
	
	private int maxAttempt = 3;
	
	private Class<? extends Throwable>[] includeExceptions;

	private Class<? extends Throwable>[] execludeExceptions;


	public boolean canRetry(int retryCount , Throwable lastExeception) {
		if(retryCount < maxAttempt-1){
			
			if(lastExeception == null){
				return includeExceptions == null || includeExceptions.length == 0;
			}
			
			if(execludeExceptions != null){
				for(Class<? extends Throwable> clazz : execludeExceptions){
					if(clazz.isAssignableFrom(lastExeception.getClass())){
						return false;
					}
				}
			}
			if(includeExceptions != null){
				for(Class<? extends Throwable> clazz : includeExceptions){
					if(clazz.isAssignableFrom(lastExeception.getClass())){
						return true;
					}
				}
			}else{
				return true;
			}
		}
		return false;
	}
	
	public boolean isLast(int retryCount,Throwable lastExeception){
		if(maxAttempt-1 == retryCount){
			if(execludeExceptions != null && execludeExceptions.length > 0){
				for(Class<? extends Throwable> clazz : execludeExceptions){
					if(clazz.isAssignableFrom(lastExeception.getClass())){
						return false;
					}
				}
			}
			if(includeExceptions == null || includeExceptions.length == 0){
				return true;
			}
			for(Class<? extends Throwable> clazz : includeExceptions){
				if(clazz.isAssignableFrom(lastExeception.getClass())){
					return true;
				}
			}
		}
		return false;
		
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
}
