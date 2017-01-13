package com.kyrincloud.koala_retry.annotation;

import com.kyrincloud.koala_retry.helper.BeanHelper;
import com.kyrincloud.koala_retry.helper.IocHelper;

public class RetryConfiguration {
	
	private String basePack;
	
	private BeanHelper beans;
	
	public RetryConfiguration(String basePack) {
		this.basePack = basePack;
		init();
	}
	
	private void init(){
		this.beans = new BeanHelper(basePack);
		IocHelper ioc = new IocHelper(beans);
	}
	
	public Object getBean(Class<?> clazz){
		return beans.getBean(clazz);
	}

}
