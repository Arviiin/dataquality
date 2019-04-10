package com.arviiin.dataquality.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

public class EmailRunnable implements Runnable {
    private String fromEmail;
    private String toEmail;
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private Map<String, Object> tableData;

    public EmailRunnable(String fromEmail,String toEmail,Map<String, Object> dataMap,
                         JavaMailSender javaMailSender,
                         TemplateEngine templateEngine) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.tableData = dataMap;
    }
    @Override
    public void run() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);//
            helper.setTo(toEmail);
            helper.setSubject("数据质量评价报告");
            Context ctx = new Context();
            ctx.setVariable("name", "数值质量量化分析平台用户");
            ctx.setVariables(tableData);
            String mail = templateEngine.process("evaluationreport.html", ctx);
            helper.setText(mail, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("发送失败");
            e.printStackTrace();
        }
    }
}
