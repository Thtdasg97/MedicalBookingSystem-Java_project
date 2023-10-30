package com.example.asm03spring.exception;

public class InvalidAppointmentException extends RuntimeException{
    public InvalidAppointmentException(String message) {
        super(message);
    }
}
