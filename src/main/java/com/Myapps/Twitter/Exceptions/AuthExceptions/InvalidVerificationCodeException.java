package com.Myapps.Twitter.Exceptions.AuthExceptions;

public class InvalidVerificationCodeException  extends RuntimeException{
    public InvalidVerificationCodeException(){
        super("Invalid verification Code");
    }
}
