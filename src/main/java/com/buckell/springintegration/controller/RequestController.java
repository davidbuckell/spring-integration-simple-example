package com.buckell.springintegration.controller;

import com.buckell.springintegration.config.IntegrationConfig.NumberGateway;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
public class RequestController {

    private final MessageChannel requestChannel;
    private final MessageChannel randomNumberChannel;
    private final NumberGateway numberGateway;

    public RequestController(MessageChannel requestChannel, MessageChannel randomNumberChannel, NumberGateway numberGateway) {
        this.requestChannel = requestChannel;
        this.randomNumberChannel = randomNumberChannel;
        this.numberGateway = numberGateway;
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

        // send will return a boolean based on if the message was successfully sent
        /*
        randomNumberChannel.send(
                MessageBuilder
                        .withPayload(request)
                        .build()
        );
        */

        // if you need to access the response from an integration flow you need to utilise the gateway
        var result = numberGateway.multiply(8);

        // if handle returned null then the request will timeout after 30 seconds.
        // this is due to the handler not producing a reply
        // this will also occur when passing in an odd number as the handler does not process the message due to filter so doesn't return anything

        return "Request sent to integration flow\n" + result;
    }
}