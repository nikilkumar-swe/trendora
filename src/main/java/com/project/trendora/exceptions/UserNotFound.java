package com.project.trendora.exceptions;


public class UserNotFound extends RuntimeException
{
    public UserNotFound(String message) {
        super(message);
    }
}
