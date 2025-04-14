package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.configuration.MQConfig;
import com.shopee.ecommerce_web.dto.custom.CustomMessage;
import com.shopee.ecommerce_web.entity.Notification;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.repository.NotificationRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void consumeNotification(CustomMessage message) {
        System.out.println(message.getMessage());
        Notification notification = new Notification();
        notification.setMessage("Ban da order thanh cong!");

        User user = userRepository.findById(message.getMessage())
                .orElseThrow(() -> new RuntimeException("User not found"));

        notification.setUser(user);
        notificationRepository.save(notification);
    }
}