package com.openrum.collector.authorization.exception;

/**
 * @description: AuthorizationException
 * @author: lou renzheng
 * @create: 2022-12-30 11:28
 **/
public class AuthorizationException extends Exception{
    public AuthorizationException() {
        super("Authentication Failed");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
