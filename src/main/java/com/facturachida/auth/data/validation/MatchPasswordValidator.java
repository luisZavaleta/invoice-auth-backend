package com.facturachida.auth.data.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.facturachida.auth.data.validation.annotation.MatchPasswords;

public class MatchPasswordValidator implements ConstraintValidator<MatchPasswords, Object> {

	 private String password;
	 private String confirmPassword;
	 
	 
	 @Override
	 public void initialize(final MatchPasswords constraintAnnotation) {
		 password = constraintAnnotation.password();
		 confirmPassword = constraintAnnotation.confirmPassword();
	 }
	
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		
		return password != null  && Objects.equals(password, confirmPassword);
		 
	}

}
