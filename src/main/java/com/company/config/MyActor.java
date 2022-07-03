package com.company.config;

import akka.actor.AbstractActor;

public class MyActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(message -> {
                    System.out.println(message);
                })
                .build();
    }
}
