package com.pedrycz.cinehub.exceptions;

public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(int pageNum) {
        super(STR."Page \{ pageNum } for requested search not found");
    }
}
