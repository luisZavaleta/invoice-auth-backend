package com.facturachida.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerService {
	
	private static final String TOPIC = "zavaleta";	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String message) {
		logger.info("PRODUCING Kafka Message  =====> " + message);
		ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send(TOPIC, message);
		 
		kafkaTemplate.flush();
		 
		 
		 future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			 
		        @Override
		        public void onSuccess(SendResult<String, String> result) {
		        	logger.info("KAFKA PRODUCER == Sent message=[" + message + 
		              "] with offset=[" + result.getRecordMetadata().offset() + "==="+result.getProducerRecord().topic() + "]");
		        }
		        @Override
		        public void onFailure(Throwable ex) {
		        	logger.info("KAFKA PRODUCER == Unable to send message=["
		              + message + "] due to : " + ex.getMessage());
		        }
		    });

		
	}
	

}
