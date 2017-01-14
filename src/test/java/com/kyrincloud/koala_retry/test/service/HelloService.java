package com.kyrincloud.koala_retry.test.service;

import com.kyrincloud.koala_retry.annotation.AutoInject;
import com.kyrincloud.koala_retry.annotation.Retry;
import com.kyrincloud.koala_retry.annotation.Service;

@Service
public class HelloService {
	
	@AutoInject
	TestService test;
	
	@Retry(value = {Exception.class},maxAttempt = 2,interval = 5000l)
	public void greet() throws Exception{
			test.sayHello();
			throw new Exception("业务性重试");
	}
}
