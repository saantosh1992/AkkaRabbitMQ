package com.company.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.company.actor.TaskManagerActor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        ActorSystem actorSystem = ActorSystem.create("MyActorSystem");
        ActorRef taskManagerActor = actorSystem.actorOf(Props.create(TaskManagerActor.class), "taskManagerActor");
        taskManagerActor.tell("Start", ActorRef.noSender());
        actorSystem.terminate();
    }
}
