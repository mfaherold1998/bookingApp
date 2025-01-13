package com.example.booking.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Enums {

    @Getter
    @AllArgsConstructor
    public enum RoleNames{
        SUPERADMIN("SUPERADMIN"),
        ADMIN("ADMIN"),
        STANDARD("STANDARD");
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum TokenType{
        EMAIL_VERIFICATION("EMAIL_VERIFICATION");
        private final String value;
    }

}