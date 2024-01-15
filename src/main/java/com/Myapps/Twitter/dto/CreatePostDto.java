package com.Myapps.Twitter.dto;

import com.Myapps.Twitter.Enum.Audience;
import com.Myapps.Twitter.Enum.ReplyRestriction;
import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Posts.Poll;
import com.Myapps.Twitter.Models.Posts.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePostDto {

    private String content;
    private Integer authorId;
    private boolean scheduled;
    private Date scheduledDate;
    private Audience audience;
    private ReplyRestriction restriction;
    private Poll poll;
}
