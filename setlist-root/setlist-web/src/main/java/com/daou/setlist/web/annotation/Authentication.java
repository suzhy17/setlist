package com.daou.setlist.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 인증 체크용 애노테이션으로 AuthCheckInterceptor에서 체크한다.
 * @author suzhy
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {
	
	AuthenticationType value() default AuthenticationType.PRODUCT;
}
