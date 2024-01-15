package com.Myapps.Twitter.Exceptions.AuthExceptions;

public class  EmailAlreadyTakenException extends RuntimeException{
    public EmailAlreadyTakenException(){
        super("Email is already taken");
    }
}
