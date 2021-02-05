package com.minimaldev.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountDoesNotExistException extends RuntimeException{
    public AccountDoesNotExistException(String message){
        super("The account number " + message + " does not exist.");
    }
}
