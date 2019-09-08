package com.jacksonfang.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Jackson Fang
 * Date:   2018/11/6
 * Time:   23:18
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    /**
     * 服务端发送验证邮件。
     *
     * @param title     邮件标题
     * @param verifyURL 激活链接
     * @param email     target email
     */
    public void sendMail(String title, String verifyURL, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setFrom(from);
        message.setTo(email);
        message.setText(verifyURL);
        mailSender.send(message);
    }


}
