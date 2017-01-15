package com.kyrincloud.koala_retry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Retry {

	Class<? extends Throwable>[] include() default {};

	Class<? extends Throwable>[] execlude() default {};
	
	int maxAttempt() default 0;
	
	long interval() default 1000l;
	
}
