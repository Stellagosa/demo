package com.stellagosa.email.service.impl;

import com.stellagosa.demo.email.service.IMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class MailServiceImplTest {

    @Autowired
    private IMailService mailService;

    @Test
    void sendEmail() {
        mailService.sendEmail();
    }

    @Test
    void sendEmailWithMultipart() throws MessagingException {
        mailService.sendEmailWithMultipart();
    }

    @Test
    void sendRichEmail() throws MessagingException {
        mailService.sendRichEmail();
    }

    @Test
    void sendTemplateEmail() throws MessagingException {
        mailService.sendTemplateEmail();
    }
}