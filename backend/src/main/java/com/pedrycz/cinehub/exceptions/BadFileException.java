package com.pedrycz.cinehub.exceptions;

public class BadFileException extends RuntimeException{
    public BadFileException(String filename) {
        super(STR."File \{ filename } is of wrong type or broken");
    }
}
