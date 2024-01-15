package com.Myapps.Twitter.Controller.Poll;

import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Service.PollServices;
import com.Myapps.Twitter.dto.CastVoteDto;
import com.Myapps.Twitter.dto.CreatePostDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poll")
@AllArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class PollController {

    private final PollServices pollServices;


    @PutMapping("/castVote")
    public ResponseEntity<ApiResponse>createPoll(@RequestBody CastVoteDto castVoteDto){
        return ResponseEntity.ok().body(pollServices.castVote(castVoteDto));
    }
}
