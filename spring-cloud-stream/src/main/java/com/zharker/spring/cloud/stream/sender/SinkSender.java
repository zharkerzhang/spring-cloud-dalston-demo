package com.zharker.spring.cloud.stream.sender;

import com.zharker.spring.cloud.stream.common.Constants;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SinkSender {

    @Output(Constants.OUTPUT_CHANNEL_NAME)
    MessageChannel output();

    @Input(Constants.INPUT_CHANNEL_NAME)
    SubscribableChannel input();
}
