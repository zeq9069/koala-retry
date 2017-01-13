package com.kyrincloud.koala_retry.helper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

import com.kyrincloud.koala_retry.annotation.AutoInject;
import com.kyrincloud.koala_retry.utils.ReflectionUtil;

public final class IocHelper {
	
	private BeanHelper beanHelper;
	
	public IocHelper(BeanHelper beanHelper) {
		this.beanHelper = beanHelper;
		init();
	}
	
	public void init(){
		//获取所有的 Bean 类和 Bean 实例对应的关系
				Map<Class<?>, Object> beanMap = beanHelper.getBeanMap();

				if (beanMap!= null && !beanMap.isEmpty()) {
					//遍历 Bean Map
					for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
						//从beanMap中获取bean和bean实例
						Class<?> beanClass = beanEntry.getKey();
						Object beanInstance = beanEntry.getValue();
						//获取所有bean类定义中的所有成员变量
						Field[] beanFields = beanClass.getDeclaredFields();
						if (beanFields != null && beanFields.length > 0) {
							//遍历 bean field
							for (Field beanField : beanFields) {
								//判断 bean Field是否带有 Inject注解
								if (beanField.isAnnotationPresent(AutoInject.class)) {
									//在 Bean Map 中获取 bean Field对应的实例
									Class<?> beanFieldClass = beanField.getType();
									Object beanFieldInstance = beanMap.get(beanFieldClass);
									if (beanFieldInstance != null) {
										//通过反射初始化BeanField的值
										ReflectionUtil.setFiled(beanInstance, beanField, beanFieldInstance);
									}
								}
							}
						}
					}
				}
	}

}
