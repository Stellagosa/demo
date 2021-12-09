package com.stellagosa.demo.email.service.impl;

import com.stellagosa.demo.email.service.IMailService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements IMailService {

    private final JavaMailSenderImpl mailSender;
    private final SpringTemplateEngine templateEngine;

    public MailServiceImpl(JavaMailSenderImpl mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    // 普通邮件
    @Override
    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2397626738@qq.com");
        message.setTo("2427230600@qq.com");
        message.setSubject("测试邮件");
        message.setText("Hello, Email Server");
        mailSender.send(message);
        System.out.println("邮件已发送！");
    }


    // 带附件的邮件
    @Override
    public void sendEmailWithMultipart() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // true 表明这是个 Multipart
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("2397626738@qq.com");
        helper.setTo("2427230600@qq.com");
        helper.setSubject("测试邮件");
        helper.setText("这是一个带有附件的邮件");

        FileSystemResource resource = new FileSystemResource("D:\\background\\201532.jpg");

        // 第一个参数是附件中的名称
        helper.addAttachment("1212.jpg", resource);
        mailSender.send(mimeMessage);
        System.out.println("邮件已发送！");
    }

    // 发送富文本内容的邮件
    @Override
    public void sendRichEmail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // true 表明这是个 Multipart
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom("2397626738@qq.com");
        helper.setTo("2427230600@qq.com");
        helper.setSubject("测试邮件");

        // cid 表明消息中会有一部分是图片并以 test 标记
        // true 表明这是个 html
        helper.setText("<html><body><img style='width:100px;height:100px' src='cid:test'><h1>Hello，富文本邮件！！！</h1></body></html>", true);

        // ClassPathResource 加载的classpath 路径下的内容
        FileSystemResource resource = new FileSystemResource("D:\\background\\492493.jpg");
        helper.addInline("test", resource);
        mailSender.send(mimeMessage);
        System.out.println("邮件已发送！");
    }

    // 发送模板邮件
    @Override
    public void sendTemplateEmail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // true 表明这是个 Multipart
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom("2397626738@qq.com");
        helper.setTo("2427230600@qq.com");
        helper.setSubject("测试邮件");

        Context context = new Context();
        context.setVariable("name", "张三");
        context.setVariable("text", "这是一封测试邮件");

        String emailTemplate = templateEngine.process("email_template", context);

        helper.setText(emailTemplate, true);

        // ClassPathResource 加载的classpath 路径下的内容
        FileSystemResource resource = new FileSystemResource("D:\\background\\492493.jpg");
        helper.addInline("test", resource);
        mailSender.send(mimeMessage);
        System.out.println("邮件已发送！");

    }







}
