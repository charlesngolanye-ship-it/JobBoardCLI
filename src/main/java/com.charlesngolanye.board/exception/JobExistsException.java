package com.charlesngolanye.board.exception;

public class JobExistsException extends RuntimeException {
    public JobExistsException(String message) {
        super(message);
    }
}
