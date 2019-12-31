package com.zharker.spring.cloud.stream.receiver;

import com.zharker.spring.cloud.stream.common.Constants;
import com.zharker.spring.cloud.stream.sender.SinkSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@Slf4j
@EnableBinding(SinkSender.class)
public class SinkReceiver {

    @StreamListener(Constants.INPUT_CHANNEL_NAME)
    public void receive(Object payload) {
        log.info("Received: " + payload);
        log.info("Recevied as String: "+new String((byte[])payload));
    }

}
