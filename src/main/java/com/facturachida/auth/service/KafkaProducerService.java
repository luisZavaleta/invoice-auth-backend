package com.facturachida.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
	
	private static final String TOPIC = "zavaleta";	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String message) {
		logger.info("PRODUCING Kafka Message  =====> " + message);
		kafkaTemplate.send(TOPIC, message);
		
	}
	

}
