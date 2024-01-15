package com.Myapps.Twitter.Exceptions.AuthExceptions;

import java.util.function.Supplier;

public class NoSuchUserException extends RuntimeException  {
    public NoSuchUserException(){
        super("User does not exists with the specified username");
    }
}
