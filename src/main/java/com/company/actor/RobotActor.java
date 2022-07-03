package com.company.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.company.message.Task;
import org.apache.log4j.Logger;

public class RobotActor extends AbstractActor {

    private static final Logger LOGGER = Logger.getLogger(RobotActor.class);
    public static class TaskDoneMessage {
        private final String status;
        private final ActorRef robot;

        public TaskDoneMessage(String status, ActorRef robot) {
            this.status = status;
            this.robot = robot;
        }

        public String getStatus() {
            return status;
        }

        public ActorRef getRobot() {
            return robot;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TaskManagerActor.TaskMessage.class, taskMessage -> {
                    Task task = taskMessage.getTask();
                    LOGGER.info("I am Robot:" + getSelf().path()+"==> I have picked the item:"
                            +task.getItemName() +" from location:"+task.getItemLocation());
                    taskMessage.getSender().tell(new RobotActor.TaskDoneMessage("Done", getSelf()), null);
                })
                .build();
    }
}
