//package com.facturachida.auth.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.facturachida.auth.service.KafkaProducerService;
//
//@RestController
//@RequestMapping(value = "/kafka")
//public class KafkaController {
//
//
//    private final KafkaProducerService producer;
//
//	@Autowired
//    KafkaController(KafkaProducerService producer) {
//        this.producer = producer;
//    }
//
//    @PostMapping(value = "/publish")
//    public String sendMessageToKafkaTopic(@RequestParam("message") String message) {
//        this.producer.sendMessage(message);
//        
//        return "Message sended";
//    }
//}
//
//
//
