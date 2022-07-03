package com.company.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AkkaSystemConfiguration {

    private static final String TASK_ACTOR_SYSTEM = "taskActorSystem";
    private static final String TASK_ACTOR_SYSTEM_CONFIGURATION = "task-akka.conf";

    public Config taskActorSystemConfiguration() {
        return ConfigFactory.parseResources(TASK_ACTOR_SYSTEM).getConfig(TASK_ACTOR_SYSTEM_CONFIGURATION);
    }
}
