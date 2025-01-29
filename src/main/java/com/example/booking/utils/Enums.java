package com.example.booking.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Enums {

    @Getter
    @AllArgsConstructor
    public enum RoleNames{

        SUPERADMIN("SUPERADMIN"),
        PROPRIETOR("PROPRIETOR"),
        EMPLOYEE("EMPLOYEE"),//User&Employee
        CLIENT("CLIENT");

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum InvitationStatus{

        PENDING("PENDING"),
        ACCEPTED("ACCEPTED"),
        REJECTED("REJECTED");

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum TokenType{

        EMAIL_VERIFICATION("EMAIL_VERIFICATION");

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum NotificationType{

        ACCEPTANCE("accepted"),
        REJECTION("rejected");

        private final String value;
    }

}