package com.pedrycz.cinehub.exceptions;

public class ReviewAlreadyExistsException extends RuntimeException {
    
    public ReviewAlreadyExistsException() {
        super("Requested user already reviewed this movie");
    }
}
