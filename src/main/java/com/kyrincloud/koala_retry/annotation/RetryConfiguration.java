package com.kyrincloud.koala_retry.annotation;

import com.kyrincloud.koala_retry.helper.BeanHelper;
import com.kyrincloud.koala_retry.helper.HelperLoad;

public class RetryConfiguration {
	
	public RetryConfiguration() {
		init();
	}
	
	private void init(){
		HelperLoad.init();
	}
	
	public Object getBean(Class<?> clazz){
		return BeanHelper.getBean(clazz);
	}

}
