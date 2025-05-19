package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    public static class EmailRequest {
        public String to;
        public String subject;
        public String body;

    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest request) {
        return emailService.sendSimpleEmail(request.to, request.subject, request.body);
    }
}