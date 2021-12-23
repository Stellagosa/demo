package com.stellagosa.demo.activiti.process.test;

import com.stellagosa.demo.activiti.process.utils.SecurityUtils;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
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

    @Override
    public void run(String... args) {
        securityUtils.loginBy("tom");
        // 分页查询定义的 process
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        logger.info("available process definitions: {}", processDefinitionPage.getTotalItems());
        for (ProcessDefinition processDefinition : processDefinitionPage.getContent()) {
            logger.info("\t process definition: {}", processDefinition);
        }
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void processText() {
        securityUtils.loginBy("tom");

        String content = pickRandomString();
        logger.info("{}: process content: {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                content);
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withName("Processing content: " + content)
                .withProcessDefinitionKey("categorizeProcess")
                .withVariable("content", content)
                .build());
        logger.info("create process instance: {}", processInstance);
    }


    @Bean
    public Connector processTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            String content = (String) inBoundVariables.get("content");
            // logic determines whether the content passes or not
            if (content.contains("activiti")) {
                integrationContext.addOutBoundVariable("approved", true);
            } else {
                integrationContext.addOutBoundVariable("approved", false);
            }
            return integrationContext;
        };
    }

    @Bean
    public Connector tagTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            String content = (String) inBoundVariables.get("content");
            content += " :) ";
            integrationContext.addOutBoundVariable("content", content);
            logger.info("final content: {}", content);
            return integrationContext;
        };
    }

    @Bean
    public Connector discardTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            String content = (String) inBoundVariables.get("content");
            content+= " :( ";
            integrationContext.addOutBoundVariable("content", content);
            logger.info("final content: {}", content);
            return integrationContext;
        };
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCompletedEvent> processCompletedListener() {
        return processCompletedEvent -> {
            ProcessInstance entity = processCompletedEvent.getEntity();
            logger.info("process completed: {}, we can send a notification to the initiator: {}",
                    entity.getName(), entity.getInitiator());
        };
    }


    private String pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        return texts[RandomUtils.nextInt(0, texts.length)];
    }

}
