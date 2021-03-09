package com.stuil.wx.mp.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @title: EmailUtil
 * @description: 邮箱
 * @date: 2021/1/29
 * @author: stuil
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */

@Component
public class EmailUtil {
    @Autowired
    JavaMailSender sender;

    public boolean sendEmail(String from,String to,String subject,String text){
        MimeMessage mimeMessage=sender.createMimeMessage();
        MimeMessageHelper helper=null;
        try {
            helper= new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
