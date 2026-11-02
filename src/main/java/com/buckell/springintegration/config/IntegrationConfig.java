package com.buckell.springintegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

    /*
        Channels determine how messages are delivered between components, including whether processing is synchronous, asynchronous, queued
        or broadcast to multiple subscribers.

        Channel	                Behaviour	                                        Typical use case
        DirectChannel	        Synchronous, point-to-point	                        Simple processing within the same thread
        QueueChannel	        Messages are stored in a queue	                    Decoupling producers and consumers
        PublishSubscribeChannel	Broadcasts messages to multiple subscribers	        Event broadcasting
        ExecutorChannel	        Asynchronous, point-to-point	                    Parallel/background processing
        PriorityChannel	        Queue with priority ordering	                    Prioritising certain messages
        RendezvousChannel	    Synchronous hand-off between sender and receiver	Coordinating producer and consumer threads
     */

    @Bean
    public MessageChannel requestChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel randomNumberChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel multiplyByTwoChannel() {
        return new DirectChannel();
    }
}