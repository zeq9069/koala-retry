package com.kyrincloud.koala_retry.annotation;

import com.kyrincloud.koala_retry.helper.BeanHelper;
import com.kyrincloud.koala_retry.helper.InterceptorHelper;
import com.kyrincloud.koala_retry.helper.IocHelper;
import com.kyrincloud.koala_retry.support.RetryContext;

public class RetryConfiguration {
	
	private String basePack;
	
	private BeanHelper beans;
	
	public RetryConfiguration(String basePack) {
		this.basePack = basePack;
		init();
	}
	
	private void init(){
		this.beans = new BeanHelper(basePack);
		RetryContext context = new RetryContext(beans.getClassHelper());
		new InterceptorHelper(beans,context);
		new IocHelper(beans);
	}
	
	public Object getBean(Class<?> clazz){
		return beans.getBean(clazz);
	}

}
