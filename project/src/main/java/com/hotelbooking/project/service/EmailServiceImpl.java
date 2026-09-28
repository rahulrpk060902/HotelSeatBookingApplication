package com.hotelbooking.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendBookingConfirmation(
            String toEmail,
            String subject,
            String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


    public void sendTemporaryPassword(
            String email,
            String temporaryPassword) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Hotel Booking - Password Reset");

        message.setText(
                "Hello,\n\n" +
                        "We received a request to reset your password.\n\n" +
                        "Your temporary password is:\n\n" +
                        temporaryPassword + "\n\n" +
                        "This temporary password is valid for 10 minutes.\n\n" +
                        "Please use it to create your new password.\n\n" +
                        "If you did not request this password reset, please ignore this email.\n\n" +
                        "Hotel Booking Team"
        );

        mailSender.send(message);
    }
}

