package com.facturachida.auth.data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.facturachida.auth.data.validation.annotation.MatchPasswords;
import com.facturachida.auth.data.validation.annotation.UniqueUsername;
import com.facturachida.auth.utils.StaticAttributes;

import lombok.Data;

@Component
@Data
@Document
@Validated
@MatchPasswords(password="password", confirmPassword="confirmPassword", message=StaticAttributes.ERROR_PASSWORD_MATCH_MEESSAGE)
public class Authuser{

	@Id
	private String id;
	
	@UniqueUsername(message=StaticAttributes.ERROR_USERNAME_UNIQUE)
	@Indexed(unique = true)
	@Field("username")
	@Email(message=StaticAttributes.ERROR_EMAIL_MEESSAGE)
	private  String username;


	@Field("password")
	@NotNull
	@Size(min = 8, message=StaticAttributes.ERROR_PASSWORD_MIN_LEN)
	private String password;

	
	@Transient
	@Field("confirmPassword")
	private String confirmPassword;


	@Field("firstname")
	@NotNull
	private  String firstname;

	@Field("lastname")
	@NotNull
	private  String lastname;
	
	
	private boolean active;
	
}