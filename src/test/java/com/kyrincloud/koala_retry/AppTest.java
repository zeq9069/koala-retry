package com.kyrincloud.koala_retry;

import java.util.Set;

import com.kyrincloud.koala_retry.utils.ClassUtil;

public class AppTest{
	
	public static void main(String[] args) {
		Set<Class<?>> clazzs = ClassUtil.getClassSet("com.kyrincloud.koala_retry");
		for(Class c : clazzs){
			System.out.println(c);
		}
	}
	
}
