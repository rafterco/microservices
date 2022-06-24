package com.raftercode.notification;

import com.raftercode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
public class NotificationController {
    public final NotificationService notificationService;

    @PostMapping
    public void send(@RequestBody NotificationRequest notificationRequest) {
        log.info("notification response {}", notificationRequest);
        notificationService.send(notificationRequest);
    }
}
