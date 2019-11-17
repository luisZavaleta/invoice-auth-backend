package com.facturachida.auth.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.facturachida.auth.config.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Component
public class SendEmailUtil  implements Serializable {	
	

	private static final long serialVersionUID = 8582113458196130542L;
	
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Value("${mail.token.duration}")
    private int tokenDuration;
	
	@Value("${mail.secret}")
	private String mailSecret;
	
	@Value("${mail.server.address}")
	private String serverAddress;

	@Value("${mail.server.port:8080}")
    private String serverPort;
	
	@Value("${mail.test_recipient}")
    private String mailTestRecipient;
	
	@Value("${mail.test.activated}")
    private Boolean mailTestActive;

	@Value("${mail.email-validation.subject}")
	private String mailSubject;
	
	@Value("${mail.email-validation.text}")
	private String mailText;

	
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
				log.error("Unable to get JWT Token  SendEmailUtil::getUsernameFromRequestToken ", e);
		
			} catch (ExpiredJwtException e) {
				log.error("JWT Token has expired  SendEmailUtil::getUsernameFromRequestToken ", e);
			}
		} else {
			log.warn("Mail Token does not begin with Bearer String");
		}
		
		return username;
	}
	
	
	
	public void sendConfirmationMail(String token) {
		
		JwtTokenUtil tokenUtil = new JwtTokenUtil(mailSecret, tokenDuration);
		
		String mailAddress;
		
		String mailFromToken = tokenUtil.getUsernameFromToken(token);
		
		
		if(mailTestActive) {
			log.info("Sending mail to test email " + mailTestRecipient + ";  Real mail is:  "+ mailFromToken);
			mailAddress = mailTestRecipient;
		}else {
			mailAddress = mailFromToken;
		}
		
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(mailAddress);	
		mail.setSubject(mailSubject);	
		mail.setText(mailText + " \n " + generateConfirmationUrl(token));
		
		
		javaMailSender.send(mail);
		
		log.info("Sending mail to ===> "+mailAddress);
		log.info("Actual mail from token  mail  ===> "+mailFromToken);
		
		
	}

	
	private String generateConfirmationUrl(String token) {
		return serverAddress + ":" + serverPort + "/" + "mail/validate?token=Bearer_"+token;
	}
	
}
