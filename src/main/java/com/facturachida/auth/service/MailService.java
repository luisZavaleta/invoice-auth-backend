package com.facturachida.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	
	@Value("${kafka.mail.topic}")
	private   String topic 	;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

}
