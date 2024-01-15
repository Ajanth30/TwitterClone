package com.Myapps.Twitter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindUsernameDto {

    private String email;
    private String phone;
    private String  username;
}
