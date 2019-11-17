package com.facturachida.auth.data.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.facturachida.auth.data.validation.UniqueUsernameValidator;

@Constraint(validatedBy = UniqueUsernameValidator.class)

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
	
	String message() default "This user already exists.";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};

}
