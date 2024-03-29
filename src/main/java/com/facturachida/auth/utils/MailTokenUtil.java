package com.facturachida.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.facturachida.auth.config.JwtTokenUtil;
import com.facturachida.auth.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;




@Slf4j
@Component
public class MailTokenUtil {
	
	JwtTokenUtil tokenUtil;	
	JwtUserDetailsService jwtUserDetailsService;
	

	@Autowired
	MailTokenUtil(
			JwtUserDetailsService jwtUserDetailsService,
			@Value("${mail.token.duration}") int tokenDuration,
			@Value("${mail.secret}") String mailSecret
			
			)
	{
		this.tokenUtil = new JwtTokenUtil(mailSecret, tokenDuration);
		this.jwtUserDetailsService = jwtUserDetailsService;
	}
	
	
	public String getJwtToken(String bearerToken) throws IllegalArgumentException{	
		if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
			return bearerToken.substring(7);
		}else {
			throw new IllegalArgumentException("Token do not start with Bearer_");
		}
	}
	
	
	public boolean isValidToken(String bearerToken) {
		
		try {
			String jwtToken = getJwtToken(bearerToken);
			String username = tokenUtil.getUsernameFromToken(jwtToken);
			
			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
			
			return tokenUtil.validateToken(jwtToken, userDetails);	
		
		}catch (IllegalArgumentException e) {
			log.error("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			log.error("JWT Token has expired");
		}
		
		return false;
	}
	
	
	
public boolean isValidToken(String username, String jwtToken) {
		
		try {
			
			UserDetails userDetails =jwtUserDetailsService.loadUserByUsername(username);
			
			return tokenUtil.validateToken(jwtToken, userDetails);	
		}catch (IllegalArgumentException e) {
			log.error("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			log.error("JWT Token has expired");
		}
		
		return false;
	}
	
	
/*
 
  @return username if the token is valid, null if it is not.
 * */
	public String getValidUsernameFromBearerToken(String bearerToken) {
		
		String jwtToken = getJwtToken(bearerToken);
		
		String username = tokenUtil.getUsernameFromToken(jwtToken);
		
		if(isValidToken(username, jwtToken)) {
			return username;
		}else {
			return null;
		}	
	}
	
}
