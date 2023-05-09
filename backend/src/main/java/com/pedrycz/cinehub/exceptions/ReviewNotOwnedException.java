package com.pedrycz.cinehub.exceptions;

public class ReviewNotOwnedException extends RuntimeException{
    public ReviewNotOwnedException() {
        super("Requested review is owned by other user!");
    }
}
