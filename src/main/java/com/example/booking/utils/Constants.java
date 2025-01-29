package com.example.booking.utils;

import com.example.booking.service.EmailService.EmailContainer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constants {

    public static class Errors {
        public static final String GENERIC = "Something went wrong";
        public static final String NOT_FOUND_WITH_ID = "%s not found with given id";
        public static final String INVALID_PAYLOAD = "Invalid payload";
        public static final String NOT_FOUND_WITH_EMAIL = "%s not found with given email";
        public static final String ACCOUNT_LOCKED = "Your account has been locked, please contact an administrator";
        public static final String ACCOUNT_DISABLED = "Your account has been disabled, please contact an administrator";
        public static final String INVALID_CREDENTIALS = "Invalid credentials, please try again";
        public static final String INVALID_REFRESH_TOKEN = "Refresh token expired, please login again";
    }

    public static EmailContainer invitationEmail(String corporationName, String subdivisionName, String token, String userEmail){

        String subject = String.format("%s are inviting you to work with them.", corporationName);

        String text = String.format(
                "Dear user,\n\n" +
                        "We are thrilled to invite you to join our team at %s in %s. Your skills and experience align perfectly with our vision, and we believe you would make a valuable addition to our team.\n\n" +
                        "To begin the onboarding process, please click on the invitation link below:\n" +
                        "https://localhost:8080/api/invitations/accept/%s\n\n" +
                        "If you have any questions or need further assistance, don’t hesitate to reach out. We look forward to welcoming you!\n\n" +
                        "Best regards,\n" +
                        "Nanda\n" +
                        "Developer\n" +
                        "Assistance",
                corporationName,
                subdivisionName,
                token
        );

        return EmailContainer.builder()
                .to(userEmail)
                .subject(subject)
                .text(text)
                .build();
    }

    public static EmailContainer invitationNotificationEmail(String type, String userEmail, String proprietorEmail, LocalDateTime dateTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, hh:mm a");
        String subject = String.format("Notification %s invitation.", type);

        String text = String.format(
                """
                        Dear owner,
                        
                        We inform you that the invitation to user %s sent at %s to work in your company has been %s.
                        Best regards,
                        Assistance""",
                userEmail,
                dateTime.format(formatter),
                type
        );

        return EmailContainer.builder()
                .to(proprietorEmail)
                .subject(subject)
                .text(text)
                .build();
    }

}
