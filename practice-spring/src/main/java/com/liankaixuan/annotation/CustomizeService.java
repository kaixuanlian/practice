package com.liankaixuan.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomizeService {

//	@AliasFor(annotation = CustomizeService.class)
	String value() default "";
}
