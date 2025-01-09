package com.edem.medlink.exception;

public class VerificationFailedException extends RuntimeException{
    public VerificationFailedException(String message) {
        super(message);
    }
}
