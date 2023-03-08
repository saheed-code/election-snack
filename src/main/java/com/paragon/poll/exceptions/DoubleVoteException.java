package com.paragon.poll.exceptions;

public class DoubleVoteException extends BusinessLogicException{
    public DoubleVoteException(String message) {
        super(message);
    }
}
