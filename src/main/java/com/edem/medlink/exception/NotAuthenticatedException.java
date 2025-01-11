package com.edem.medlink.exception;

public class NotAuthenticatedException extends RuntimeException{
    public  NotAuthenticatedException (String message){
        super(message);
    }
}
