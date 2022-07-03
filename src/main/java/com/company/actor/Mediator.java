package com.company.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.company.config.Constants;
import com.company.message.Task;
import com.company.pushlish.ActiveMQMessageListener;
import org.apache.log4j.Logger;

import javax.jms.JMSException;

public class Mediator {

    private static final Logger LOGGER = Logger.getLogger(Mediator.class);

    private static ActorRef taskManagerActor = null;
    public static void createManager(String message) {
        ActorSystem actorSystem = ActorSystem.create("MyActorSystem");
        taskManagerActor = actorSystem.actorOf(Props.create(TaskManagerActor.class), "taskManagerActor");
        taskManagerActor.tell(message, ActorRef.noSender());
    }

    public static ActorRef getTaskManagerActor() {
        return taskManagerActor;
    }

    public static void assignTaskToManager(Task task) {
        try {
            taskManagerActor.tell(task, ActorRef.noSender());
        } catch (Exception e) {
            LOGGER.error("Error while assigning the task to Robot:{}", e);
        }
    }

    public static void startTaskPicker() throws JMSException, InterruptedException {
        ActiveMQMessageListener activeMQMessageListener = new ActiveMQMessageListener();
        activeMQMessageListener.startListener(Constants.CLIENT_ID, Constants.QUEUE_NAME);
    }
}
