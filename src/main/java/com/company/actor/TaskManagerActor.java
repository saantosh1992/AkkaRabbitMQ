package com.company.actor;

import akka.actor.*;
import com.company.message.Task;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TaskManagerActor extends AbstractActor {

    private static final Logger LOGGER = Logger.getLogger(TaskManagerActor.class);

    private static final String ROBOT_NAME = "taskRobot$";

    List<ActorRef> actorPool = new ArrayList<>();

    private void createRobots(int max) {
        IntStream.rangeClosed(1, max).forEach(index -> {
           ActorRef robot =  createTaskRobot(ROBOT_NAME + index);
           actorPool.add(robot);
        });
    }

    private ActorRef createTaskRobot(String robotName) {
        return getContext().actorOf(Props.create(RobotActor.class), robotName);
    }
    public static class TaskMessage {
        private final Task task;
        private final ActorRef sender;

        public  TaskMessage(Task task, ActorRef sender) {
            this.task = task;
            this.sender = sender;
        }

        public Task getTask() {
            return task;
        }

        public ActorRef getSender() {
            return sender;
        }
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("START", msg-> createRobots(10))
                .match(Task.class, msg -> {
                    int index = new Random().nextInt(10);
                    LOGGER.info("Total Robot available:"+actorPool.size());
                    if (index < actorPool.size()) {
                        ActorRef robot = actorPool.get(index);
                        removeRobotFromPool(robot);
                        TaskMessage taskMessage = new TaskMessage(msg, getSelf());
                        LOGGER.info("Assigning the task:" + msg + "to Robot:" + robot.path());
                        robot.tell(taskMessage, null);
                    }
                }).match(RobotActor.TaskDoneMessage.class, recMsg -> {
                    LOGGER.info("Task is "+recMsg.getStatus() +" by Robot:"+recMsg.getRobot().path());
                    addRobotToPool(recMsg.getRobot());
                })
                .build();
    }

    private void removeRobotFromPool(ActorRef robot) {
        LOGGER.info("Removing the assigned task robot from pool:"+robot.path());
        actorPool.removeIf(actorRef -> actorRef.path().equals(robot.path()));
    }

    private void addRobotToPool(ActorRef robot) {
        actorPool.add(robot);
    }

}
