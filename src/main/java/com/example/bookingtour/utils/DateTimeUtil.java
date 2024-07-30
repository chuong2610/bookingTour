package com.example.bookingtour.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString, String patten){
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern(patten);
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }

}
