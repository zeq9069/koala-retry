package com.kyrincloud.koala_retry.service;

import com.kyrincloud.koala_retry.annotation.AutoInject;
import com.kyrincloud.koala_retry.annotation.Service;

@Service
public class HelloService {
	
	@AutoInject
	TestService test;
	
	public void greet(){
		test.sayHello();
	}

}
