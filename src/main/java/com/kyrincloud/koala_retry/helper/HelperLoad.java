package com.kyrincloud.koala_retry.helper;

import com.kyrincloud.koala_retry.utils.ClassUtil;

public class HelperLoad {
	
	public static void init(){
		Class<?>[] helpers = {ClassHelper.class,BeanHelper.class,InterceptorHelper.class,IocHelper.class};
		for(Class<?> clazz:helpers){
			ClassUtil.loadClass(clazz.getName(),true);
		}
	}

}
