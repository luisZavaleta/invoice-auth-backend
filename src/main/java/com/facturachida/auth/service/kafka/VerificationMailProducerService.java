package com.facturachida.auth.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.facturachida.auth.repository.UserRepository;
import com.facturachida.auth.utils.StaticAttributes;
import com.facturachida.auth.utils.SendEmailUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VerificationMailProducerService {


    private String topic = StaticAttributes.MAIL_TOPIC;
	private String receiveTopic = StaticAttributes.MAIL_RECEIVE_TOPIC;
	
	@Autowired 
	SendEmailUtil sendEmailUtil;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	public void sendMessage(String token) {
		
		log.info("VerificationMailProducerService::sendMessage Sending kafka message with topic: " + topic);
		
		ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send(topic, token);
		
		 future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			 
		        @Override
		        public void onSuccess(SendResult<String, String> result) {
		        	log.info("SUCESSFUL == Sent message=[" + token + "]" );
		        }
		        @Override
		        public void onFailure(Throwable ex) {
		        	log.error("FAILURES == Sent message=[" + token + "]" + ex.getMessage() );
		        }
		    });			
	}
	
	
	
	public boolean verificateMail(String token) {
		
		String username = sendEmailUtil.getUsernameFromRequestToken(token);
		
		log.info("VerificationMailProducerService::verificateMail Sending kafka message for mail with topic: " + receiveTopic + " to "+ username);
		
		if(username != null) {			
			kafkaTemplate.send(receiveTopic, username);
			return true;
		}
		
		return false;		
	}
	
	

	public void sendMailToResetPasswordProducer(String token) {
		
		log.info("VerificationMailProducerService::sendMailToResetPasswordProducer Sending kafka message with topic: " + topic);
		
		ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send(StaticAttributes.SEND_MAIL_TO_RESET_PASSWORD_TOPIC, token);
		
		
		 future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			 
		        @Override
		        public void onSuccess(SendResult<String, String> result) {
		        	log.info("VerificationMailProducerService::sendMailToResetPasswordProducer sucessfully sent message "
		        			+ "to reset password  [" + token + "]" );
		        }
		        @Override
		        public void onFailure(Throwable ex) {
		        	log.error("VerificationMailProducerService::sendMailToResetPasswordProducer fail while sending  "
		        			+ "message to reset password =[" + token + "]" + ex.getMessage() );
		        }
		    });			
	}
	
}
