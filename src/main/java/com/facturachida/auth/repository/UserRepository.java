package com.facturachida.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.facturachida.auth.data.User;

public interface UserRepository  extends MongoRepository<User, Long>{

}