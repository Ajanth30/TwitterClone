package com.Myapps.Twitter.ServiceImpl;

import com.Myapps.Twitter.Exceptions.AuthExceptions.NoSuchUserException;
import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Image.Image;
import com.Myapps.Twitter.Models.Posts.Poll;
import com.Myapps.Twitter.Models.Posts.Posts;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Repository.PollRepository;
import com.Myapps.Twitter.Repository.PostRepository;
import com.Myapps.Twitter.Repository.UserRepository;
import com.Myapps.Twitter.Service.PollServices;
import com.Myapps.Twitter.Service.PostService;

import com.Myapps.Twitter.Utils.Image.FileServices;
import com.Myapps.Twitter.dto.CreatePostDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;

    private final UserRepository userRepo;

    private final PollRepository pollRepo;

    private final FileServices fileServices;

    private final PollServices pollServices;

    @Override
    @Transactional
    public ApiResponse createPost(CreatePostDto createPostDto) {
        try{
            ApplicationUser author=userRepo.findById(createPostDto.getAuthorId()).get();
            Poll poll=createPostDto.getPoll();
            if(poll!=null){
                poll=pollServices.createPoll(poll);
            }
            Posts posts= Posts.builder()
                    .content(createPostDto.getContent())
                    .postDate(new Date())
                    .author(author)
                    .scheduled(createPostDto.isScheduled())
                    .scheduledDate(createPostDto.getScheduledDate())
                    .restriction(createPostDto.getRestriction())
                    .audience(createPostDto.getAudience())
                    .poll(poll)
                    .build();
            poll.setPosts(posts);
            return ApiResponse.success(postRepo.save(posts));

        }catch (Exception e){
            e.printStackTrace();
            return ApiResponse.failure(e.getMessage());

        }
    }

    @Override
    public ApiResponse createPostWithMedia(String requestValues, List<MultipartFile> media) throws IOException {

            ObjectMapper objectMapper=new ObjectMapper();
            CreatePostDto createPostDto=objectMapper.readValue(requestValues,CreatePostDto.class);
            ApplicationUser author=userRepo.findById(createPostDto.getAuthorId()).get();
            Set<Image> images=media.stream().map(file-> {
                        try {
                            return fileServices.UploadImage(file,"post");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).
                    collect(Collectors.toSet());
            Posts post= Posts.builder()
                    .content(createPostDto.getContent())
                    .postDate(createPostDto.getScheduledDate()!=null?createPostDto.getScheduledDate():new Date())
                    .author(author)
                    .images(images)
                    .scheduled(createPostDto.isScheduled())
                    .scheduledDate(createPostDto.getScheduledDate())
                    .restriction(createPostDto.getRestriction())
                    .audience(createPostDto.getAudience())
                    .build();
            return ApiResponse.success(postRepo.save(post));


    }

    @Override
    public ApiResponse getAllPostByAuthorId(Integer authorId) {

        try{
            ApplicationUser author=userRepo.findById(authorId).get();
            Posts posts=postRepo.findByAuthor(author).get();
            return ApiResponse.success(posts);

        }catch (Exception e){
            e.printStackTrace();
            return ApiResponse.failure(null);
        }
    }

    @Override
    public ApiResponse getPostById(Integer postId) {
        try{
            return ApiResponse.success(postRepo.findById(postId));

        }catch (Exception e){
            e.printStackTrace();
            return ApiResponse.failure(null);
        }
    }

    @Override
    public ApiResponse getAllPosts() {
        try{
            return ApiResponse.success(postRepo.findAll());

        }catch (Exception e){
            e.printStackTrace();
            return ApiResponse.failure(null);

        }
    }

    @Override
    public ApiResponse deletePost(Integer postId) {
        try{
            postRepo.deleteById(postId);
            return ApiResponse.success(true);

        }catch (Exception e){
            return ApiResponse.failure(false);

        }
    }
}
