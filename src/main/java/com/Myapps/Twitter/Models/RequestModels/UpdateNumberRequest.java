package com.Myapps.Twitter.Models.RequestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateNumberRequest {

    private String userName;
    private String phoneNumber;
}
