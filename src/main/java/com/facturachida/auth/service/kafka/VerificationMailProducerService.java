package com.facturachida.auth.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class VerificationMailProducerService {

	
	//private static final String TOPIC = "";	
	private static final Logger logger = LoggerFactory.getLogger(VerificationMailProducerService.class);
	
	@Value("${mail.token.topic}")
    private String topic;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String token) {
		
		logger.info("Sending kafka message with topic: " + topic);
		
		ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send(topic, token);
		
		
		 future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			 
		        @Override
		        public void onSuccess(SendResult<String, String> result) {
		        	logger.info("SUCESSFUL == Sent message=[" + token + "]" );
		        }
		        @Override
		        public void onFailure(Throwable ex) {
		        	logger.error("FAILURES == Sent message=[" + token + "]" + ex.getMessage() );
		        }
		    });
		
		
		
		
		
	}
	
	
	
}
