package com.Myapps.Twitter.Service;

import com.Myapps.Twitter.Models.Response.ApiResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface MailService {

    ApiResponse sendMail(String to,long body);
}
