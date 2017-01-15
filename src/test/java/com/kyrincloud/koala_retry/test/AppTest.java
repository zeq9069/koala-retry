package com.kyrincloud.koala_retry.test;

import com.kyrincloud.koala_retry.annotation.RetryConfiguration;
import com.kyrincloud.koala_retry.test.service.HelloService;

public class AppTest{
	
	public static void main(String[] args) {
		RetryConfiguration config = new RetryConfiguration();
		HelloService service = (HelloService) config.getBean(HelloService.class);
		try {
			service.greet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
