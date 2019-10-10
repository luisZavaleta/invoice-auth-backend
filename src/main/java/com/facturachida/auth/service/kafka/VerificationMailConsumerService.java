package com.facturachida.auth.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.facturachida.auth.data.Authuser;
import com.facturachida.auth.repository.UserRepository;
import com.facturachida.auth.utils.SendEmailUtil;


@Service
public class VerificationMailConsumerService {
	
	@Value("${mail.token.topic}")
	private static final String TOPIC = "sendverificationmail";
	
	
	@Value("${mail.token.recieve.topic}")
	private static final String RECEIVE_TOPIC = "recieveverificationmail";
	
	private static final Logger logger = LoggerFactory.getLogger(VerificationMailConsumerService.class);
	
	@Value("${mail.secret}")
	String mailSecret;
	
	@Value("${mail.token.duration}")
	Integer tokenDuration;
	
	
	@Autowired
	SendEmailUtil sendMailUtil;
	
	
	@Autowired
	UserRepository userRepository;
	
	VerificationMailConsumerService(){
		
	}
	
	@KafkaListener(topics=TOPIC, groupId="verification_mail")
	public void consume(String token)  {
		
		
		sendMailUtil.sendConfirmationMail(token);
	
		
		logger.info("=====MAIL SENDED====");
	}
	
	
	
	@KafkaListener(topics=RECEIVE_TOPIC, groupId="verification_mail")
	public void activateUser(String username) {
		
		Authuser user = userRepository.findByUsername(username);	
		user.setActive(true);
		
		logger.info("USER ID========>"+user.getId());
		
		userRepository.save(user);
		
		logger.info("=====User with Username ===="+ username + " is active");
	
	}

}
