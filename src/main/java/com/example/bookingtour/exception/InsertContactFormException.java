package com.example.bookingtour.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertContactFormException extends RuntimeException{
    private String message;
}
