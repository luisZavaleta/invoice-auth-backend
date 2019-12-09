package com.facturachida.auth.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturachida.auth.config.JwtTokenUtil;

@RestController
@CrossOrigin
@RequestMapping(value="/home")
public class AuthController {
	
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private HttpServletRequest request;
	

	@GetMapping
	@CrossOrigin
	public Map<String, Object> testToken() {
		
		Map<String, Object> reponseMap = new HashMap<String, Object>();
		
		
		String requestTokenHeader = request.getHeader("Authorization");	
		
		String token = "";
		
		if(requestTokenHeader!= null && requestTokenHeader.length() > 7) {
			 token = requestTokenHeader.substring(7);
		}
		
		
		
		Date now = new Date();
		Date tokenExpire = jwtTokenUtil.getExpirationDateFromToken(token);
		
	
		String pattern = "MM-dd-yyyy HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
	


		reponseMap.put("validUntil", simpleDateFormat.format(tokenExpire));
		reponseMap.put("currentDate", simpleDateFormat.format(now));
		reponseMap.put("difference", (tokenExpire.getTime() - now.getTime())/1000);
		reponseMap.put("token", requestTokenHeader);
		
	
		return reponseMap;
	} 

}
