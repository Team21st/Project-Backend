package com.CS353.cs353project.exception;

public class SendMailException extends RuntimeException {
    public SendMailException(String message) {
        super(message);
    }
}
