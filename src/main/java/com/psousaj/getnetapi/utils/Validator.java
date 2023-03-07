package com.psousaj.getnetapi.utils;

enum Status {
    APPROVED,
    AUTHORIZED,
    PENDING,
    CONFIRMED,
    CANCELED,
    DENIED,
    ERROR
}

public class Validator {

    public static boolean validateStatus(String status) {
        try {
            Status.valueOf(status.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}