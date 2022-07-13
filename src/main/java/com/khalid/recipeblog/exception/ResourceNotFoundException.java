package com.khalid.recipeblog.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("resource not found!!");
    }

}
