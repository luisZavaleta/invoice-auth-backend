package com.facturachida.auth.data.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.facturachida.auth.data.validation.MatchPasswordValidator;

@Constraint(validatedBy = MatchPasswordValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPasswords {
		
	   
	 
	    Class<?>[] groups() default {};
	 
	    Class<? extends Payload>[] payload() default {};
	    
	    String message() default "Passwords do not match.";
	    String password();
	    String confirmPassword();

}



