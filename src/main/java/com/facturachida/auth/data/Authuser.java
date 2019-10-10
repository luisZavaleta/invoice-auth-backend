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

import lombok.Data;

@Component
@Data
@Document
@Validated
@MatchPasswords(password="password", confirmPassword="confirmPassword")
public class Authuser{

	@Id
	private String id;
	
	@UniqueUsername
	@Indexed(unique = true)
	@Field("username")
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
	
	
	private boolean active;
	
}