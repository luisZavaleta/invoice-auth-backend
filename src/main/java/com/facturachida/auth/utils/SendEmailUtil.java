package com.facturachida.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;

import com.facturachida.auth.config.JwtTokenUtil;
import com.facturachida.auth.data.AuthUser;
import com.facturachida.auth.service.JwtUserDetailsService;

public class SendEmailUtil {
	
	@Autowired
    private JavaMailSender javaMailSender;
	private AuthUser user;
	
	
	@Value("${mail.secret}")
	private String secret;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;
	
	
	
	public SendEmailUtil(AuthUser user) {
		this.user = user;
	}
	
	public void sendMail(String email) {
		
	
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(email);
		
		
		JwtTokenUtil tu = new JwtTokenUtil(secret, 60*60*24*7);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());	
		
		String mailToken = tu.generateToken(userDetails);
		
		mail.setSubject("Plase verify your Email");
		
		mail.setText("Please folllow this link: \n " + generateConfirmationUrl(mailToken));
		
		javaMailSender.send(mail);
		
		
		
	}
	

	
	private String generateConfirmationUrl(String token) {
		
		return serverAddress + ":" + serverPort + "/" + "mail/verification?token="+token;
	}
	
	

}
