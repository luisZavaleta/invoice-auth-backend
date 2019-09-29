package com.facturachida.auth.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturachida.auth.config.JwtTokenUtil;
import com.facturachida.auth.data.AuthUser;
import com.facturachida.auth.data.JwtRequest;
import com.facturachida.auth.data.JwtResponse;
import com.facturachida.auth.service.JwtUserDetailsService;
import com.facturachida.auth.service.kafka.VerificationMailProducerService;
import com.facturachida.auth.utils.SendEmailUtil;


@RestController
@CrossOrigin
public class JwtAuthenticationController {	

	@Autowired
	private AuthenticationManager authenticationManager;	

	@Autowired
	private JwtTokenUtil jwtTokenUtil;	

	@Autowired
	private JwtUserDetailsService userDetailsService;	
	
	@Autowired
	SendEmailUtil sendMailUtil;
	
	@Autowired
	VerificationMailProducerService  verificationMailProducerService;
	
	
	@Value("${mail.secret}")
	String mailSecret;
	
	@Value("${mail.token.duration}")
	Integer tokenDuration;
	


	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
			
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());	

			final UserDetails userDetails = 
				userDetailsService.loadUserByUsername(authenticationRequest.getUsername());	

				final String token = jwtTokenUtil.generateToken(  userDetails);		

				return ResponseEntity.ok(new JwtResponse(token));

	}	

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@Valid @RequestBody AuthUser user) throws Exception {
		
		user =  userDetailsService.save(user);		
		
		
		//sendMailUtil.setUser(user);
		
		user.setPassword("");
		user.setConfirmPassword("");
		
		verificationMailProducerService.sendMessage(sendMailUtil.getMailToken(userDetailsService.loadUserByUsername(user.getUsername())));
		
		
		
		return ResponseEntity.ok(user);
	}
	
	
	@RequestMapping(value = "/mailVerification", method = RequestMethod.GET)
	public ResponseEntity<?> validateMail(@RequestParam String token) throws Exception {
		
		
		JwtTokenUtil tu = new JwtTokenUtil(mailSecret, tokenDuration);
		
		token  = token.substring(7);
	
		
		return ResponseEntity.ok(tu.getUsernameFromToken(token));
	}


	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}