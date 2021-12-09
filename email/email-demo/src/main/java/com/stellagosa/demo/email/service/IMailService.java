package com.stellagosa.demo.email.service;

import javax.mail.MessagingException;

public interface IMailService {

    void sendEmail();

    void sendEmailWithMultipart() throws MessagingException;

    void sendRichEmail() throws MessagingException;

    void sendTemplateEmail() throws MessagingException;
}
