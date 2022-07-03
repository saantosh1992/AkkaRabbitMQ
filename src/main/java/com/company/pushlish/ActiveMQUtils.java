package com.company.pushlish;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public final class ActiveMQUtils {

    private ActiveMQUtils() {
        // private constructor.
    }

    public static Session getSession(Connection connection, String clientId) {
        try {
            if (connection != null) {
                if (connection.getClientID() == null) {
                    connection.setClientID(clientId);
                }
                // create a Session
                return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            }
        } catch (JMSException jmse) {
            System.out.println("Error while creating the session:" + jmse);
        }
        return null;
    }

    public static Connection getConnection() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        activeMQConnectionFactory.setTrustAllPackages(true);
        try {
            return activeMQConnectionFactory.createConnection();
        } catch (JMSException e) {
            System.out.println("Error while creating the connection:" + e);
        }
        return null;
    }

    public static MessageProducer getMessagePublisher(String queueName, Session session) throws JMSException {
        Queue queue = session.createQueue(queueName);
        return session.createProducer(queue);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                System.out.println("Error while closing the connection:" + e);
            }
        }
    }
}
