package com.facturachida.auth.data;


import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class User{


	@Field("username")
	private  String username;

	@Field("password")
	private  String password;
	
}