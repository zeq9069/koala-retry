## 重试项目 （进行中）
项目中经常用到重试的功能，经常冗余一些代码很恶心，所以单独提取出来一个小框架专门做重试处理  

#### 功能特性（目前）：
 1. 提供Ioc功能，非spring项目可以很好的使用Ioc项目，使用重试功能，提供的注解如：@Service 、@Retry 、@AutoInject
 2. 可以和spring项目很好的结合，很好的使用重试的功能
 3. 可以按照时间的间隔进行重试（后期提供指数回避的重试策略）
 4. 可以选择性重试，可以根据每次调用方法传入的参数，选择性的决定这次调用是否进行重试。
 
#####待完成目标 :
 
 1.  重试策略的结构优化，以及新的重试策略的实现
 2.  代码结构调整
 3.  回避策略调整(目前仅仅支持固定时间间隔重试)
 4.  recover实现
 5. spring以来方式的调整优化
 
#####已完成目标：  
  1. <del>选择性重试策略的添加(每次调用会根据参数判断，此次用不用重试)</del>
  2. <del>spring的支持</del>
  
#### 非spring使用方式：
  
	package com.kyrincloud.koala_retry.test.service;
    
	import com.kyrincloud.koala_retry.annotation.AutoInject;
	import com.kyrincloud.koala_retry.annotation.Retry;
	import com.kyrincloud.koala_retry.annotation.Service;
	
	@Service
	public class HelloService {
		@AutoInject
		TestService test;
		@Retry(execlude = {ArrayIndexOutOfBoundsException.class},maxAttempt = 3,interval = 1000l , thisOneRetry = 0)
		public void greet(boolean retry) throws Exception{
			System.out.println("greet");
				test.sayHello();
				throw new Exception("业务性重试");
		}
	}


	package com.kyrincloud.koala_retry.test.service;
	
	import com.kyrincloud.koala_retry.annotation.Retry;
	import com.kyrincloud.koala_retry.annotation.Service;
	
	@Service
	public class TestService {
		
		@Retry(maxAttempt = 2,interval = 1000l)
		public void sayHello(){
			System.out.println("Hello kyrin!");
		}
	
	}

	package com.kyrincloud.koala_retry.test;
	
	import com.kyrincloud.koala_retry.annotation.RetryConfiguration;
	import com.kyrincloud.koala_retry.test.service.HelloService;
	
	public class AppTest{
		
		public static void main(String[] args) throws Exception {
			RetryConfiguration config = new RetryConfiguration();
			HelloService service = (HelloService) config.getBean(HelloService.class);
			
			service.greet(true);
		}
	}

	
#### spring使用方式（待优化）：	


	@EnableAspectJAutoProxy
	@Configuration
	@ComponentScan(basePackages="test.test")
	public class App 
	{ 
		
		@Bean
		public SpringRetryAspectJ get(){
			return new SpringRetryAspectJ();
		}
		
		public static void main(String[] args) throws Exception {
		    ApplicationContext annotationContext = new AnnotationConfigApplicationContext(App.class);
		    annotationContext.getBean(SpringRetryAspectJ.class);
		    RetryTest remoteService = annotationContext.getBean(RetryTest.class);
		    remoteService.say(true);
		    
		}
	}



	package test.test;
	
	import org.springframework.remoting.RemoteAccessException;
	import org.springframework.retry.annotation.Retryable;
	import org.springframework.stereotype.Service;
	
	import com.kyrincloud.koala_retry.annotation.Retry;
	
	import org.springframework.retry.annotation.Backoff;
	
	@Service
	public class RetryTest {
	
		@Retry(include = {Exception.class},interval=1000l,maxAttempt=3,thisOneRetry = 0)
		public void say(boolean thisOneRetry) throws Exception {
		        System.out.println("Hello Kyrin ！");
		        throw new Exception("调用异常");
		}
	}
	