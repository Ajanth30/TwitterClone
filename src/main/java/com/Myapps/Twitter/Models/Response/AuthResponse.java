package com.Myapps.Twitter.Models.Response;

import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {

    private String token;
    private ApplicationUser user;


}
