package com.example.notificationservices.notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificationMessageListener {
    static final Logger logger = LoggerFactory.getLogger(NotificationMessageListener.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private NotificationsRepository notificationsRepository;

    @RabbitListener(queues = RabbitConfig.QUEUE_NOTIFICATIONS)
    public void processNotification(Notification notification) {
        logger.info("Notification Received: {}",notification.toString());
        sendEmail(notification);
        logger.info("Email sent to : {}",notification.getNotificationEmail());
        notification.setNotificationStatus(NotificationStatus.NOTIFICATION_SENT);
        notificationsRepository.save(notification);
        logger.info("Notification saved : {}",notification.getNotificationId());

    }

    private void sendEmail(Notification notification) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(notification.getNotificationEmail());

        msg.setSubject("Notificacion Recibida");
        StringBuilder sb=new StringBuilder("La orden número ");
        sb.append(notification.getOrderId().toString());
        sb.append(" fue recibida exitosamente.");
        msg.setText(sb.toString());

        javaMailSender.send(msg);
    }
}
