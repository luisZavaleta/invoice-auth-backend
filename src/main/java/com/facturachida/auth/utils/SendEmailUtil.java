package com.facturachida.auth.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.facturachida.auth.config.JwtTokenUtil;
import com.facturachida.auth.data.AuthUser;


@Component
public class SendEmailUtil  implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8582113458196130542L;


	@Autowired
    private JavaMailSender javaMailSender;
	
	
	private AuthUser user;
	
	
	@Value("${jwt.secret}")
	private String mailSecret;
	
	@Value("${mail.server.address}")
	private String serverAddress;

	@Value("${server.port:8080}")
    private String serverPort;
    
    public void setUser(AuthUser user) {
    	this.user = user;
    }
	
	
	
	public SendEmailUtil(AuthUser user, String mailSecret, String serverAddress,String serverPort ) {
		this.user = user;
		this.mailSecret = mailSecret;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}
	
	public SendEmailUtil() {
		
	}
	
	
	public void sendMail(UserDetails userDetails, String email) {
		
	
		
		System.out.println("USER USERNAME ===>" +user.getUsername());
		
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(email);
		
		
		JwtTokenUtil tu = new JwtTokenUtil(mailSecret, 60*60*24*7);
		
		String mailToken = tu.generateToken(userDetails);
		
		mail.setSubject("Plase verify your Email");
		
		mail.setText("Please folllow this link: \n " + generateConfirmationUrl(mailToken));
		
		
		javaMailSender.send(mail);
		
		
		
	}
	

	
	private String generateConfirmationUrl(String token) {
		
		return serverAddress + ":" + serverPort + "/" + "mailVerification?token=Bearer "+token;
	}
	
	

}
