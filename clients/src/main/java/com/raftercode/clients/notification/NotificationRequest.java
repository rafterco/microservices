package com.raftercode.clients.notification;

public record NotificationRequest(
        Integer toCustomerId,
        String email,
        String message
) {
}