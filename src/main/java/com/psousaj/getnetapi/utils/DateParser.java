package com.psousaj.getnetapi.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {
    public static String parseDate(String date) {
        String[] patterns = { "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss'Z'", "ddMMyyyy" };
        for (String pattern : patterns) {
            try {
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern))
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
                try {
                    return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }
}
