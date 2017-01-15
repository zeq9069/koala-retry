package com.kyrincloud.koala_retry.policy;

import com.kyrincloud.koala_retry.support.AbstractRetryContext;

public class TimeoutRetryPolicy {

	
	
	
	
	class TimeoutContext extends AbstractRetryContext{
		
		private long timeout;
		
		private long start;
		
		public TimeoutContext(int timeout) {
			super();
			this.timeout = timeout;
			this.start = System.currentTimeMillis();
		}
		
		public boolean isTimeout(){
			return (System.currentTimeMillis() - start) >= timeout;
		}
	}
}
