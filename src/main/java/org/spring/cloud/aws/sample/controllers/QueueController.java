package org.spring.cloud.aws.sample.controllers;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.spring.cloud.aws.sample.dtos.CreateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class QueueController {

    private static final String DEFAULT_QUEUE_NAME = "test-spring-cloud-aws-sdk";

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public QueueController(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    @RequestMapping(
            path = "/sqs/message",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity addMessage(@RequestBody @Valid CreateMessage createMessage){
        this.queueMessagingTemplate.convertAndSend(DEFAULT_QUEUE_NAME,createMessage);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}