package com.facturachida.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSenderService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	String kafkaTopic = "auth_mail";
	
	public void send(String message) {
	    
	    kafkaTemplate.send(kafkaTopic, message);
	}
}
