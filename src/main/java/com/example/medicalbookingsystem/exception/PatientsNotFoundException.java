package com.example.medicalbookingsystem.exception;

public class PatientsNotFoundException extends RuntimeException {
    public PatientsNotFoundException(String message) {
        super(message);
    }

    public PatientsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
