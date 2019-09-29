package com.facturachida.auth.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.facturachida.auth.config.JwtTokenUtil;
import com.facturachida.auth.utils.SendEmailUtil;


@Service
public class VerificationMailConsumerService {
	
	@Value("${mail.token.topic}")
	private static final String TOPIC = "sendverificationmail";
	
	private static final Logger logger = LoggerFactory.getLogger(VerificationMailConsumerService.class);
	
	@Value("${mail.secret}")
	String mailSecret;
	
	@Value("${mail.token.duration}")
	Integer tokenDuration;
	
	
	@Autowired
	SendEmailUtil sendMailUtil;
	
	VerificationMailConsumerService(){
		
	}
	
	@KafkaListener(topics=TOPIC, groupId="verification_mail")
	public void consume(String token)  {
		
		
		sendMailUtil.sendConfirmationMail(token);
	
		
		logger.info("=====MAIL SENDED====");
	}

}
