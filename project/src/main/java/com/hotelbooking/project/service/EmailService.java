package com.hotelbooking.project.service;

public interface EmailService {
    void sendBookingConfirmation(
            String toEmail,
            String subject,
            String body
    );
}

