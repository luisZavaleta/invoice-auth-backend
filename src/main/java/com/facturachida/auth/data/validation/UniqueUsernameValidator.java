package com.facturachida.auth.data.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.facturachida.auth.data.AuthUser;
import com.facturachida.auth.data.validation.annotation.UniqueUsername;
import com.facturachida.auth.repository.UserRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		
		System.out.println(value);
		
		AuthUser ua = userRepository.findByUsername(value);		
		
		return ua == null;
	
	}

}
