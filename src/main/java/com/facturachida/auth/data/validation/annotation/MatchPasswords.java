package com.facturachida.auth.data.validation.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.facturachida.auth.data.validation.MatchPasswordValidator;

@Constraint(validatedBy = MatchPasswordValidator.class)

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface MatchPasswords {
	
	   String message() default
	      "Both passwords should be the same";
	 
	    Class<?>[] groups() default {};
	 
	    Class<? extends Payload>[] payload() default {};
	    
	    String password();
	    String confirmPassword();

}


