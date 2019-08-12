package com.facturachida.auth.data.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

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
		
		
		 try {
			String pwd = BeanUtils.getProperty(obj, password);
			String confPwd = BeanUtils.getProperty(obj, confirmPassword);
			
			return pwd != null  && Objects.equals(pwd, confPwd);
			
			
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
		
		 
	}

}
