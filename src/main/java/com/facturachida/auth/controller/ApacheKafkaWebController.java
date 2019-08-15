package com.facturachida.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.facturachida.auth.service.KafkaSenderService;

public class ApacheKafkaWebController {
	
	@Autowired
	KafkaSenderService kafkaSenderService;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("message") String message) {
		kafkaSenderService.send(message);

		return "Message sent to the Kafka Topic 'auth_mail' Successfully";
	}


}
