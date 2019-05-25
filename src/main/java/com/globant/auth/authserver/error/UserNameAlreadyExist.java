package com.globant.auth.authserver.error;

public class UserNameAlreadyExist extends RuntimeException {

    public UserNameAlreadyExist(String message) {
        super(message);
    }
}
