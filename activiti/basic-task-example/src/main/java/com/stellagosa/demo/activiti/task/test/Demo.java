package com.stellagosa.demo.activiti.task.test;

import com.stellagosa.demo.activiti.task.utils.SecurityUtils;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/21 22:00
 * @Description:
 */
@Component
public class Demo implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private TaskRuntime taskRuntime;

    @Override
    public void run(String... args) {
        securityUtils.loginBy("tom");

        Task task = taskRuntime.create(TaskPayloadBuilder.create()
                .withName("First Task")
                .withDescription("First Demo Task")
                .withCandidateGroup("ACTIVITITEAM")
                .build());
        logger.info("First Task Created: {}", task);

        securityUtils.loginBy("other");
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        logger.info("other person have {} tasks", tasks.getTotalItems());

        securityUtils.loginBy("rose");
        tasks = taskRuntime.tasks(Pageable.of(0, 10));
        logger.info("rose have {} tasks", tasks.getTotalItems());

        Task claimTask = taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(tasks.getContent().get(0).getId()).build());
        logger.info("rose claiming a task: {}", claimTask);

        Task completedTask = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(tasks.getContent().get(0).getId()).build());
        logger.info("rose completed a task: {}", completedTask);
    }

    @Bean
    public TaskRuntimeEventListener<TaskAssignedEvent> taskAssignedListener() {
        return taskAssigned -> {
            Task entity = taskAssigned.getEntity();
            logger.info("{} have claimed by {}, ",
                    entity.getId(),
                    entity.getAssignee());
        };
    }

    @Bean
    public TaskRuntimeEventListener<TaskCompletedEvent> taskCompletedListener() {
        return taskCompleted -> {
            Task entity = taskCompleted.getEntity();
            logger.info("{} have completed by {}, ",
                    entity.getId(),
                    entity.getAssignee());
        };
    }
}
