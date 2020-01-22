package com.example.notificationservices.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationMessageSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public NotificationMessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendNotification(Notification notification) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NOTIFICATIONS, notification);
        /*
        try {
            String notificationJson = objectMapper.writeValueAsString(notification);
            Message message = MessageBuilder
                                .withBody(notificationJson.getBytes())
                                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                                .build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NOTIFICATIONS, message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        */
    }
}