package com.paragon.poll.exceptions;

public class UserAlreadyExistException extends BusinessLogicException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
