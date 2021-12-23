package com.stellagosa.demo.activiti.full.nobean.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellagosa.demo.activiti.full.nobean.utils.SecurityUtils;
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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ObjectMapper objectMapper;

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

        LinkedHashMap<String, String> content = pickRandomString();
        logger.info(">>> {}: process content: {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                content);
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withName("Processing content: " + content)
                .withProcessDefinitionKey("categorizeHumanProcess")
                .withVariable("content", objectMapper.convertValue(content, JsonNode.class))
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
                taskRuntime.claim(TaskPayloadBuilder.claim()
                        .withTaskId(task.getId())
                        .build());

                List<VariableInstance> variables = taskRuntime.variables(TaskPayloadBuilder.variables()
                        .withTaskId(task.getId())
                        .build());
                VariableInstance variableInstance = variables.get(0);

                if (variableInstance.getName().equals("content")) {
                    LinkedHashMap<String, Object> contentToProcess = objectMapper.convertValue(variableInstance.getValue(), new TypeReference<LinkedHashMap<String, Object>>() {});
                    logger.info(">>> The content received in the task needs to be approved: {}", contentToProcess);

                    if (contentToProcess.get("body").toString().contains("activiti")) {
                        logger.info(">>> The content approved");
                        contentToProcess.put("approved", true);
                    } else {
                        logger.info(">>> The content discarded");
                        contentToProcess.put("approved", false);
                    }
                    taskRuntime.complete(TaskPayloadBuilder.complete()
                            .withTaskId(task.getId()).withVariable("content", objectMapper.convertValue(contentToProcess, JsonNode.class))
                            .build());
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
            LinkedHashMap<String, Object> content = objectMapper.convertValue(inBoundVariables.get("content"), new TypeReference<LinkedHashMap<String, Object>>() {
            });
            content.put("tags", Collections.singletonList(" :) "));
            integrationContext.addOutBoundVariable("content", content);
            logger.info(">>> Final content: {}", content);
            return integrationContext;
        };
    }

    @Bean
    public Connector discardTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            LinkedHashMap<String, Object> content = objectMapper.convertValue(inBoundVariables.get("content"), new TypeReference<LinkedHashMap<String, Object>>() {
            });
            content.put("tags", Collections.singletonList(" :( "));
            integrationContext.addOutBoundVariable("content", content);
            logger.info("final content: {}", content);
            return integrationContext;
        };
    }

    private LinkedHashMap<String, String> pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        String text = texts[RandomUtils.nextInt(0, texts.length)];
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("body", text);
        return map;
    }
}
