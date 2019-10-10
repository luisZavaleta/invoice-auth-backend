package com.facturachida.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturachida.auth.service.kafka.VerificationMailProducerService;

@RestController
@CrossOrigin
@RequestMapping(value="/mail")
public class VerificateController {
	
	
	@Autowired
	VerificationMailProducerService verificationMailProducerService;
	
	
	@GetMapping(value="/validate")
	public Map<String, String> validateMail(@RequestParam("token") String  token) {
		
		Map<String, String> m = new HashMap<String, String>();
		
		
		if(verificationMailProducerService.verificateMail(token)) {
			m.put("status", "validated");
		}else {
			m.put("status", "error");
		}
		
		
		
		return m;
		
		
	} 
	

}
