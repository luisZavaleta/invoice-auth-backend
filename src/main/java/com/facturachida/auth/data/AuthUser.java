package com.facturachida.auth.data;


import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Data
@Document

public class AuthUser{


	@Field("username")
	private  String username;

	@Field("password")
	private  String password;
	
}