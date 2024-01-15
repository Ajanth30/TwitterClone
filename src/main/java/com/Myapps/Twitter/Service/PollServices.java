package com.Myapps.Twitter.Service;


import com.Myapps.Twitter.Models.Posts.Poll;
import com.Myapps.Twitter.Models.Posts.Posts;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.dto.CastVoteDto;
import com.Myapps.Twitter.dto.CreatePostDto;

public interface PollServices {

    Poll createPoll(Poll poll);
    ApiResponse castVote(CastVoteDto castVoteDto);
}
