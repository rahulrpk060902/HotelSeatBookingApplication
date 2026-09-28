package com.hotelbooking.project.service;

public interface EmailService {
    void sendBookingConfirmation(
            String toEmail,
            String subject,
            String body
    );

    void sendTemporaryPassword(String email, String temporaryPassword);
}

