package com.company.pushlish;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.company.actor.Mediator;
import com.company.actor.TaskManagerActor;
import com.company.message.Task;
import org.apache.log4j.Logger;

import javax.jms.*;

public class ActiveMQMessageListener implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(ActiveMQMessageListener.class);

    public String startListener(String clientId, String queueName) throws JMSException, InterruptedException {
        Connection connection = ActiveMQUtils.getConnection();
        connection.start();
        Session session = ActiveMQUtils.getSession(connection, clientId);
        Queue queue = session.createQueue(queueName);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new ActiveMQMessageListener());
        Thread.sleep(1000);
        return clientId;
    }

    @Override
    public void onMessage(Message message) {

        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
               Task task = (Task) objectMessage.getObject();
                if (task != null) {
                    LOGGER.info("Received Task to be assigned to Robot:{}" + task);
                    sendTaskToManager(task);
                }

            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void sendTaskToManager(Task task) {
        Mediator.assignTaskToManager(task);
    }

}
