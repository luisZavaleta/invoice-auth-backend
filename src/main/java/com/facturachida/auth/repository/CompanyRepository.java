package com.facturachida.auth.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.facturachida.auth.data.Company;

public interface CompanyRepository  extends MongoRepository<Company, Long>{

}
