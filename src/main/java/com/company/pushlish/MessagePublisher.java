package com.company.pushlish;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessagePublisher {

    public void sendMessage( String clientId, String queue, String message)
            throws JMSException {
        // create a JMS TextMessage
        Connection connection = ActiveMQUtils.getConnection();
        Session newSession = ActiveMQUtils.getSession(connection, clientId);
        if (newSession != null) {
            TextMessage textMessage = newSession.createTextMessage(message);
            MessageProducer messageProducer = ActiveMQUtils.getMessagePublisher(queue, newSession);
            // send the message to the topic destination
            messageProducer.send(textMessage);
            System.out.println(clientId + ": sent message with text='{}'"+ message);
        }
    }

}
