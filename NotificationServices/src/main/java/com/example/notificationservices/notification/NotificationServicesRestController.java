package com.example.notificationservices.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class NotificationServicesRestController {

    private final NotificationMessageSender notificationMessageSender;

    @Autowired
    public NotificationServicesRestController(NotificationMessageSender notificationMessageSender) {
        this.notificationMessageSender = notificationMessageSender;
    }

    @PostMapping("/sendnotification")
    public String  sendNotification(@RequestBody Notification notification, RedirectAttributes redirectAttributes){
        notificationMessageSender.sendNotification(notification);
        redirectAttributes.addFlashAttribute("message", "Notification message sent successfully");
        return "redirect:/";
        
        /*Notification savedNotification = notificationsRepository.save(notification);

            this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NOTIFICATIONS, savedNotification);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{notificationId}")
                .buildAndExpand(savedNotification.getNotificationId()).toUri();

        return ResponseEntity.created(location).build();*/
    }


    


}
