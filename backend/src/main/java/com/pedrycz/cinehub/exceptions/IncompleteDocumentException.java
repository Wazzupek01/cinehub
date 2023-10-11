package com.pedrycz.cinehub.exceptions;

public class IncompleteDocumentException extends RuntimeException {

    public IncompleteDocumentException() {
        super("Document data is incomplete");
    }
}
