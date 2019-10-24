package com.facturachida.auth.data;

import lombok.Data;

@Data
public class ReponseUser {
	
	private String id;
	private  String username;
	private  String firstname;
	private  String lastname;
	private boolean active;
	private int status;
	

}
