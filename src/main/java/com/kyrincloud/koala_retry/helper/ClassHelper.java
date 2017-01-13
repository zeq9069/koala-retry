package com.kyrincloud.koala_retry.helper;

import java.lang.annotation.Annotation;
import java.util.Set;

import com.google.common.collect.Sets;
import com.kyrincloud.koala_retry.annotation.Service;
import com.kyrincloud.koala_retry.utils.ClassUtil;

public class ClassHelper {
	
	private static Set<Class<?>> CLAZZS;
	
	private String basePack;
	
	public ClassHelper(String basePack) {
		this.basePack = basePack;
		init();
	}
	
	private void init(){
		CLAZZS = ClassUtil.getClassSet(basePack);
	}
	
	public Set<Class<?>> getBeans(){
		Set<Class<?>> beans = Sets.newHashSet();
		beans.addAll(getServices());
		return beans;
	}
	
	public Set<Class<?>> getServices(){
		Set<Class<?>> services = Sets.newHashSet();
		for(Class<?> clazz : CLAZZS){
			if(clazz.isAnnotationPresent(Service.class)){
				services.add(clazz);
			}
		}
		return services;
	}
	
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
		Set<Class<?>> classSet = Sets.newHashSet();
		for (Class<?> clazz : CLAZZS) {
			if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
				classSet.add(clazz);
			}
		}
		return classSet;
	}

	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classSet = Sets.newHashSet();
		for (Class<?> clazz : CLAZZS) {
			if (clazz.isAnnotationPresent(annotationClass)) {
				classSet.add(clazz);
			}
		}
		return classSet;
	}

}
