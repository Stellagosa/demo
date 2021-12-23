package com.stellagosa.demo.activiti.full.bean.test;

import cn.hutool.json.JSONUtil;
import com.stellagosa.demo.activiti.full.bean.entity.Content;
import com.stellagosa.demo.activiti.full.bean.utils.SecurityUtils;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;

    @Override
    public void run(String... args) {
        securityUtils.loginBy("system");
        // 分页查询定义的 process
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        logger.info(">>> available process definitions: {}", processDefinitionPage.getTotalItems());
        for (ProcessDefinition processDefinition : processDefinitionPage.getContent()) {
            logger.info("\t >>> process definition: {}", processDefinition);
        }
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void processText() {
        securityUtils.loginBy("system");
        Content content = pickRandomString();
        logger.info(">>> {}: process content: {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                content);
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withName("Processing content: " + content)
                .withProcessDefinitionKey("categorizeHumanProcess")
                .withVariable("content", JSONUtil.toJsonStr(content))
                .build());
        logger.info(">>> create process instance: {}", processInstance);
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void checkAndWorkOnTasksWhenAvailable() {
        securityUtils.loginBy("salaboy");
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        if (tasks.getTotalItems() > 0) {
            for (Task task : tasks.getContent()) {
                logger.info(">>> claiming task: {}", task.getId());
                if (task.getStatus() != Task.TaskStatus.ASSIGNED) {
                    taskRuntime.claim(TaskPayloadBuilder.claim()
                            .withTaskId(task.getId())
                            .build());

                    List<VariableInstance> variables = taskRuntime.variables(TaskPayloadBuilder.variables()
                            .withTaskId(task.getId())
                            .build());
                    VariableInstance variableInstance = variables.get(0);

                    if (variableInstance.getName().equals("content")) {
                        Content content = JSONUtil.toBean(variableInstance.getValue().toString(), Content.class);
                        logger.info(">>> The content received in the task needs to be approved: {}", content);

                        if (content.getBody().contains("activiti")) {
                            logger.info(">>> The content approved");
                            content.setApproved(true);
                        } else {
                            logger.info(">>> The content discarded");
                            content.setApproved(false);
                        }
                        taskRuntime.complete(TaskPayloadBuilder.complete()
                                .withTaskId(task.getId()).withVariable("content", content)
                                .build());
                    }
                }
            }
        } else {
            logger.info(">>> There are no task for me to work on");
        }
    }

    @Bean
    public Connector tagTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            Content content = (Content) inBoundVariables.get("content");
            content.getTags().add(" :) ");
            integrationContext.addOutBoundVariable("content", content);
            logger.info(">>> Final content: {}", content);
            return integrationContext;
        };
    }

    @Bean
    public Connector discardTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            Content content = (Content) inBoundVariables.get("content");
            content.getTags().add(" :( ");
            integrationContext.addOutBoundVariable("content", content);
            logger.info("final content: {}", content);
            return integrationContext;
        };
    }

    private Content pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        String text = texts[RandomUtils.nextInt(0, texts.length)];
        return new Content(text, false, new ArrayList<>());
    }
}
