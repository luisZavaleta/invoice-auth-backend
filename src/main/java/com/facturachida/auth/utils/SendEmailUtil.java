package com.facturachida.auth.utils;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.facturachida.auth.config.JwtTokenUtil;
import com.facturachida.auth.service.kafka.VerificationMailConsumerService;

import io.jsonwebtoken.ExpiredJwtException;


@Component
public class SendEmailUtil  implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8582113458196130542L;
	
	//private static final int tokenDuration = 60*60*24*7;


	@Autowired
    private JavaMailSender javaMailSender;
	
	
	@Value("${mail.token.duration}")
    private int tokenDuration;
	
	
	//private AuthUser user;
	
	
	@Value("${mail.secret}")
	private String mailSecret;
	
	@Value("${mail.server.address}")
	private String serverAddress;

	@Value("${server.port:8080}")
    private String serverPort;
	
	@Value("${mail.test_recipient}")
    private String mailTestRecipient;
	
	@Value("${mail.test.activated}")
    private Boolean mailTestActive;
	
	private static final Logger logger = LoggerFactory.getLogger(VerificationMailConsumerService.class);
    
 

	
	public SendEmailUtil() {
		
	}
	
	

	
	public String getMailToken(UserDetails userDetails) {
		
		
		JwtTokenUtil tokenUtil = new JwtTokenUtil(mailSecret, tokenDuration);
		
		String mailToken = tokenUtil.generateToken(userDetails);
		
		return mailToken;
		
		
	}
	
	
	public String getUsernameFromRequestToken(String requestToken) {
		
		JwtTokenUtil tokenUtil = new JwtTokenUtil(mailSecret, tokenDuration);
		
		String jwtToken;
		String username = null;
		
		if (requestToken != null && requestToken.startsWith("Bearer_")) {
			jwtToken = requestToken.substring(7);
			
			try {
				
				username = tokenUtil.getUsernameFromToken(jwtToken);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("Mail Token does not begin with Bearer String");
		}
		
		return username;
	}
	
	
	
	public void sendConfirmationMail(String token) {
		
		JwtTokenUtil tokenUtil = new JwtTokenUtil(mailSecret, tokenDuration);
		
		String mailAddress;
		
		String mailFromToken = tokenUtil.getUsernameFromToken(token);
		
		
		if(mailTestActive) {
			mailAddress = mailTestRecipient;
		}else {
			
			mailAddress = mailFromToken;
		}
		
		
	
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(mailAddress);
		
		mail.setSubject("Facturachida.com is requesting e-mail verification ");
		
		mail.setText("Please validate your email in FacturaChida.com so you can use the app: \n " + generateConfirmationUrl(token));
		
		
		javaMailSender.send(mail);
		
		logger.info("Sending mail to ===>"+mailAddress);
		logger.info("Receiver  mail  ===>"+mailFromToken);
		
		
	}

	
	private String generateConfirmationUrl(String token) {
		
		return serverAddress + ":" + serverPort + "/" + "mail/validate?token=Bearer_"+token;
	}
	
	

}
