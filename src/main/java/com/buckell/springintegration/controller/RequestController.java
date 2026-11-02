package com.buckell.springintegration.controller;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
public class RequestController {

    private final MessageChannel requestChannel;
    private final MessageChannel randomNumberChannel;

    public RequestController(MessageChannel requestChannel, MessageChannel randomNumberChannel) {
        this.requestChannel = requestChannel;
        this.randomNumberChannel = randomNumberChannel;
    }

    @PostMapping
    public String createRequest(@RequestBody String request) {

        /*
        requestChannel.send(
                MessageBuilder
                        .withPayload(request)
                        .build()
        );
        */

        randomNumberChannel.send(
                MessageBuilder
                        .withPayload(request)
                        .build()
        );

        return "Request sent to integration flow\n";
    }
}