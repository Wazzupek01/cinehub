package com.pedrycz.cinehub.exceptions;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String paramValue) {
        super(STR. "Document with parameter of value \{ paramValue } not found" );
    }
}
