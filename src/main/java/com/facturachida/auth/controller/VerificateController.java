package com.facturachida.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturachida.auth.data.Authuser;
import com.facturachida.auth.data.ResetPasswordRequest;
import com.facturachida.auth.repository.UserRepository;
import com.facturachida.auth.service.kafka.VerificationMailProducerService;
import com.facturachida.auth.utils.MailTokenUtil;
import com.facturachida.auth.utils.SendEmailUtil;


@RestController
@CrossOrigin
@RequestMapping(value="/mail")
public class VerificateController {
	
	

	private final VerificationMailProducerService verificationMailProducerService;
	private final SendEmailUtil sendEmailUtil;	
	private final MailTokenUtil mailTokenUtil;
	private final UserRepository userRepository;
	
	@Autowired
	public VerificateController(
				VerificationMailProducerService verificationMailProducerService, 
				SendEmailUtil sendEmailUtil,
				MailTokenUtil mailTokenUtil,
				UserRepository userRepository
			) 
	{
		this.verificationMailProducerService = verificationMailProducerService;
		this.sendEmailUtil = sendEmailUtil;
		this.mailTokenUtil = mailTokenUtil;
		this.userRepository = userRepository;
		
		
	}
	
	
	
	@GetMapping(value="/validate")
	public Map<String, String> validateMail(@RequestParam("token") String  token) {
		
		Map<String, String> validateResponse = new HashMap<String, String>();
		
		
		if(verificationMailProducerService.verificateMail(token)) {
			validateResponse.put("status", "validated");
		}else {
			validateResponse.put("status", "error");
		}
		
		return validateResponse;
	} 
	
	
	
	@PostMapping(value="/resetpassword")
	public ResponseEntity<?> resetMail(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
		
		String username = mailTokenUtil.getValidUsernameFromBearerToken(resetPasswordRequest.getToken());
		
		Authuser user = null;
		if(username != null ) {
			user = userRepository.findByUsername(username);
			user.setPassword(resetPasswordRequest.getPassword());
			userRepository.save(user);
		}
		
		
		return  ResponseEntity.ok(user);
	} 
}
