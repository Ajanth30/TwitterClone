package com.Myapps.Twitter.dto;

import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Posts.PollOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CastVoteDto {

    private Integer optionId;
    private Integer voterId;
}
