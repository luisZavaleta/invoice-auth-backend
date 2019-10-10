package com.facturachida.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.facturachida.auth.data.Authuser;

public interface UserRepository  extends MongoRepository<Authuser, Long>{
	
		public Authuser findByUsername(String username);
		
	
}