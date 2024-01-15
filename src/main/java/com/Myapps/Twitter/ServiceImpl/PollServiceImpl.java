package com.Myapps.Twitter.ServiceImpl;

import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Posts.Poll;
import com.Myapps.Twitter.Models.Posts.PollOptions;
import com.Myapps.Twitter.Models.Posts.Posts;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Repository.PollOptionsRepository;
import com.Myapps.Twitter.Repository.PollRepository;
import com.Myapps.Twitter.Repository.UserRepository;
import com.Myapps.Twitter.Service.PollServices;
import com.Myapps.Twitter.dto.CastVoteDto;
import com.Myapps.Twitter.dto.CreatePostDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PollServiceImpl implements PollServices {

    private final PollRepository polRepo;

    private final UserRepository userRepository;
    private final PollOptionsRepository pollOptionsRepo;


    @Override
    public Poll createPoll(Poll poll) {
        try{
            Set<PollOptions> optionsSet=poll.getOptionsSet()
                    .stream()
                    .peek(option-> option.setPoll(poll)).collect(Collectors.toSet());
            poll.setOptionsSet(optionsSet);
            return polRepo.save(poll);


        }catch (Exception e){
            e.printStackTrace();
            return null;

        }


    }

    @Override
    @Transactional
    public ApiResponse castVote(CastVoteDto castVoteDto) {
        try{
            PollOptions option=pollOptionsRepo.findById(castVoteDto.getOptionId()).get();
            Set<ApplicationUser> voters=option.getVoters();
            voters.add(userRepository.findById(castVoteDto.getVoterId()).get());
            option.setVoters(voters);
            pollOptionsRepo.save(option);
            return ApiResponse.success(true);


        }
        catch (Exception e){
            e.printStackTrace();
            return ApiResponse.failure(false);
        }
    }
}
