package com.javeriana.proyecto.proyecto.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }

}
