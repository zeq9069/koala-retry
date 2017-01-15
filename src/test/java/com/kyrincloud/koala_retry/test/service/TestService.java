package com.kyrincloud.koala_retry.test.service;

import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.annotation.Service;

@Service
public class TestService {
	
	@Retry(maxAttempt = 2,interval = 5000l)
	public void sayHello(){
		System.out.println("Hello kyrin!");
	}

}
