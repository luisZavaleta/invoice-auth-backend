package com.facturachida.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.facturachida.auth.data.AuthUser;

public interface UserRepository  extends MongoRepository<AuthUser, Long>{
	
		AuthUser findByUsername(String username);
}