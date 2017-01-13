package com.kyrincloud.koala_retry;

import com.kyrincloud.koala_retry.annotation.RetryConfiguration;
import com.kyrincloud.koala_retry.service.HelloService;

public class AppTest{
	
	public static void main(String[] args) {
		RetryConfiguration config = new RetryConfiguration("com.kyrincloud.koala_retry");
		HelloService service = (HelloService) config.getBean(HelloService.class);
		service.greet();
	}
	
}
