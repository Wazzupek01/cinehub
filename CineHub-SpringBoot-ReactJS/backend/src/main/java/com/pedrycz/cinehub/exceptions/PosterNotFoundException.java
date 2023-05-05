package com.pedrycz.cinehub.exceptions;

public class PosterNotFoundException extends RuntimeException{
    public PosterNotFoundException(String poster) {
        super("Poster with name '" + poster + "' not found");
    }
}
