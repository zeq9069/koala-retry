package com.kyrincloud.koala_retry.policy;

import java.util.concurrent.TimeUnit;

public class Sleep {

	private long interval ;
	
	public Sleep(long interval) {
		this.interval = interval;
	}
	
	public void sleep() {
		try {
			TimeUnit.MILLISECONDS.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
