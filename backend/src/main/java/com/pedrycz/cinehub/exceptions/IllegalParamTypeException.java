package com.pedrycz.cinehub.exceptions;

public class IllegalParamTypeException extends RuntimeException {
    public IllegalParamTypeException(Class<?> given, Class<?> expected) {
        super("Param is of wrong type: " + given.getSimpleName() + ", expected: " + expected.getSimpleName());
    }
}
