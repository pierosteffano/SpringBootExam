package com.example.notificationservices.notification;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Api(value="/notification",description="Notification services",produces ="application/json")
@RequestMapping("/notification")
@RestController
public class NotificationServicesRestController {

    private final NotificationMessageSender notificationMessageSender;

    @Autowired
    public NotificationServicesRestController(NotificationMessageSender notificationMessageSender) {
        this.notificationMessageSender = notificationMessageSender;
    }

    @ApiOperation(value="send notification",response= String.class)
    @ApiResponses(value={
            @ApiResponse(code=201,message="Notification message sent successfully",response=String.class),
    })
    @PostMapping("/send")
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
