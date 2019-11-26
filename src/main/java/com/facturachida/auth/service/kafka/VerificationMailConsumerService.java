package com.facturachida.auth.service.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.facturachida.auth.data.Authuser;
import com.facturachida.auth.repository.UserRepository;
import com.facturachida.auth.utils.StaticAttributes;
import com.facturachida.auth.utils.SendEmailUtil;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Service
public class VerificationMailConsumerService {
	
	
	@Value("${mail.secret}")
	String mailSecret;
	
	@Value("${mail.token.duration}")
	Integer tokenDuration;
	
	
	@Autowired
	SendEmailUtil sendMailUtil;
	
	
	@Autowired
	UserRepository userRepository;
	

	@KafkaListener(topics=StaticAttributes.MAIL_TOPIC, groupId=StaticAttributes.MAIL_GROUP_ID)
	public void consume(String token)  {
		sendMailUtil.sendConfirmationMail(token);
	}
	
	
	@KafkaListener(topics=StaticAttributes.MAIL_RECEIVE_TOPIC, groupId=StaticAttributes.MAIL_GROUP_ID)
	public void activateUser(String username) {
		
		Authuser user = userRepository.findByUsername(username);	
		user.setActive(true);		
		userRepository.save(user);
		
		log.info("=====User with Username ===="+ username + " is now active");
	}
	
	
	@KafkaListener(topics=StaticAttributes.SEND_MAIL_TO_RESET_PASSWORD_TOPIC, groupId=StaticAttributes.MAIL_GROUP_ID)
	public void sendMailToResetPasswordConsumer(String token)  {
		sendMailUtil.sendConfirmationMailToResetPassword(token);
	}
	

}
