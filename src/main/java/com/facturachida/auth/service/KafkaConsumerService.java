package com.facturachida.auth.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service 
public class KafkaConsumerService {
	
	private static final String TOPIC = "zavaleta";
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
	
	
	@KafkaListener(topics=TOPIC, groupId="group_id") 
	public void consume(String message) throws IOException{
		logger.info("CONSUMING Kafka Message  =====> " + message);
	}
	

}
