package com.shopee.ecommerce_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("cmoon90532056@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return text;  // gửi thành công trả về nội dung text
        } catch (MailException ex) {
            // Xử lý lỗi, trả về thông báo lỗi
            return "Gửi mail thất bại: " + ex.getMessage();
        }
    }
}
