package com.buckell.springintegration.integration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
public class IntegrationFlows {

    /*
      transform: map one type to another
      handle: execute business logic
      filter: only process messages that match condition?
    */

    @Bean
    public IntegrationFlow requestFlow() {

        return IntegrationFlow
                .from("requestChannel")
                .handle(message -> {
                    System.out.println("Integration flow received: "
                            + message.getPayload());
                })
                .get();
    }

    @Bean
    public IntegrationFlow getRandomNumber() {

        return IntegrationFlow
                .from("randomNumberChannel")
                .transform(message -> 7) // results in exception "no output-channel or replyChannel header available" if no output channel specified after transform
                .log(message -> "generated random number was: " + message.getPayload())
                //.handle(message -> {
//                    System.out.println("generated random number was: "
//                            + message.getPayload());
//                }) // calling handle consumes messages & prevents it from being passed onto further channels
                // can use wireTap instead of handle to observe or copy a message without interrupting its journey through the flow
                //.wireTap(flow -> flow.handle(message ->
//                    System.out.println("generated random number was: "
//                            + message.getPayload())))
                .channel("multiplyByTwoChannel")
                // instead of replacing handle with wireTap, just make use of log instead
                .get();

        /*
        Method	        Purpose	Produces                                output?
        transform()	    Changes the message payload	                    Yes
        handle()	    Executes business logic	                        Not necessarily
        filter()	    Determines whether a message should continue	Only if accepted
        enrichHeaders()	Adds or modifies message headers	            Yes
        route()	        Routes messages to different channels	        Routes existing message
         */
    }

    @Bean
    public IntegrationFlow multiplyByTwo(MessageSource messageSource) {

        return IntegrationFlow
                .from("multiplyByTwoChannel")
                .<Integer>filter(message -> message % 2 == 0)
                // if number was odd (7) the filter rejects it with following logged message
                // The message [GenericMessage [payload=7, headers={id=c8750136-8b7c-c16a-acfa-05b8bf330759, timestamp=1793577474247}]] has been rejected in filter: bean 'multiplyByTwo.filter#0' for component 'multiplyByTwo.org.springframework.integration.config.ConsumerEndpointFactoryBean#0'; from source: 'public org.springframework.integration.dsl.IntegrationFlow com.buckell.springintegration.integration.IntegrationFlows.multiplyByTwo(org.springframework.context.MessageSource)'
                .<Integer>handle((payload, headers) -> {
                    var result = payload * 2;
                    System.out.println("multiplied number is even: " + result);
                    return result;
                })
                .get();
    }
}