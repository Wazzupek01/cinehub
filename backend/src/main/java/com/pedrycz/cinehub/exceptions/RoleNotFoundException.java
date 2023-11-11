package com.pedrycz.cinehub.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role) {
        super(STR."Role with name \{ role } doesn't exist");
    }
}
