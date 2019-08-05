package com.facturachida.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.facturachida.auth.data.Company;
import com.facturachida.auth.repository.CompanyRepository;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class HelloWorldController {
	
	@Autowired
	private CompanyRepository cr;
	
	@RequestMapping("/hello")
    public List<Company> hello() {
	
		Company companyA = new Company(5l, "Hola", "Mundo");
		Company companyB = new Company(6l, "Luis", "Zavaleta");
		Company companyC = new Company(7l, "Mungus", "Fungus");
		
		cr.save(companyA);
		cr.save(companyB);
		cr.save(companyC);
		
        return cr.findAll(); 
    }

}


