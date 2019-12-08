package com.facturachida.auth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturachida.auth.data.Authuser;
import com.facturachida.auth.data.ReponseUser;
import com.facturachida.auth.data.ResetPasswordRequest;
import com.facturachida.auth.repository.UserRepository;
import com.facturachida.auth.service.JwtUserDetailsService;
import com.facturachida.auth.service.kafka.VerificationMailProducerService;
import com.facturachida.auth.utils.MailTokenUtil;
import com.facturachida.auth.utils.SendEmailUtil;


@RestController
@CrossOrigin
@RequestMapping(value="/mail")
public class VerificateController {
	
	

	private final VerificationMailProducerService verificationMailProducerService;
	private final MailTokenUtil mailTokenUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final SendEmailUtil sendMailUtil;
	private final JwtUserDetailsService userDetailsService;
	
	@Autowired
	public VerificateController(
				VerificationMailProducerService verificationMailProducerService, 
				MailTokenUtil mailTokenUtil,
				UserRepository userRepository,
				PasswordEncoder passwordEncoder,
				SendEmailUtil sendMailUtil,
				JwtUserDetailsService userDetailsService
			) 
	{
		this.verificationMailProducerService = verificationMailProducerService;
		this.mailTokenUtil = mailTokenUtil;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.sendMailUtil = sendMailUtil;
		this.userDetailsService = userDetailsService;
	}
	
	
	
	@GetMapping(value="/validate")
	public Map<String, String> validateMail(@RequestParam("token") String  token) {
		
		Map<String, String> validateResponse = new HashMap<String, String>();
		
		if(verificationMailProducerService.verificateMail(token)) {
			validateResponse.put("status", "validated");
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
			
			Consumer<Authuser> userConsumer = u -> u.setPassword(passwordEncoder.encode(u.getPassword()));
			userConsumer.accept(user);
			
			userRepository.save(user);
		}
		
		ReponseUser responseUser = new ReponseUser();
		
		responseUser.setStatus(200);
		responseUser.setUsername(user.getUsername());
		responseUser.setActive(user.isActive());
		
		return  ResponseEntity.ok(responseUser);
	} 
	
	
	
	@PostMapping(value="/resendverification")
	public ResponseEntity<?> resendValidationMail(@RequestBody Map<String,Object> body) {
		
		Authuser user = userRepository.findByUsername(body.get("username").toString());
		
		if(user == null) {
			return new ResponseEntity<String>("Username not found",HttpStatus.UNPROCESSABLE_ENTITY);
		}
	
		verificationMailProducerService.sendMessage(sendMailUtil.getMailToken(userDetailsService.loadUserByUsername(user.getUsername())));
		
		return ResponseEntity.ok("Mail sended");
		
	}
	
}
