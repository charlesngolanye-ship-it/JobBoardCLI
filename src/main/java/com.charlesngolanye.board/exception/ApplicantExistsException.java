package com.charlesngolanye.board.exception;

public class ApplicantExistsException extends RuntimeException {
    public ApplicantExistsException(String message) {
        super(message);
    }
}
