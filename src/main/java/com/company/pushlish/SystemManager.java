package com.company.pushlish;

import com.company.actor.Mediator;
import com.company.config.Constants;

import javax.jms.JMSException;

public class SystemManager {
    public static void main(String[] args) throws JMSException, InterruptedException {
        startTaskManager(Constants.START_COMMAND);
    }

    private static void startTaskManager(String startCommand) throws JMSException, InterruptedException {
        Mediator.createManager(startCommand);
        Mediator.startTaskPicker();
    }
}
