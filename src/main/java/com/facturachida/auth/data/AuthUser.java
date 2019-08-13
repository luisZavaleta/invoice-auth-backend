package com.facturachida.auth.data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.validation.annotation.Validated;

import com.facturachida.auth.data.validation.annotation.MatchPasswords;

import lombok.Data;

@Data
@Document
@Validated
@MatchPasswords(password="password", confirmPassword="confirmPassword")
public class AuthUser{

	@Field("username")
	@NotNull(message="mail should not be null")
	@Email(message="this field shouls be an e-mail")
	private  String username;


	@Field("password")
	@NotNull
	@Size(min = 8, message="password whould be at leat 8 characters long")
	String password;

	
	@Transient
	@Field("confirmPassword")
	String confirmPassword;


	@Field("firstname")
	@NotNull
	private  String firstname;

	@Field("lastname")
	@NotNull
	private  String lastname;
	
}