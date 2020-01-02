package com.zharker.spring.cloud.stream.controller;

import com.zharker.spring.cloud.stream.sender.SinkSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(value = {SinkSender.class})
@RestController
public class SenderController {

    @Autowired
    private SinkSender sinkSender;

    @RequestMapping("/send/{message}")
    public boolean send(@PathVariable("message") String message){
        return sinkSender.output().send(MessageBuilder.withPayload(message.getBytes()).build());
    }
}
