package com.Myapps.Twitter.Controller.Posts;

import com.Myapps.Twitter.Models.Posts.Posts;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Repository.PostRepository;
import com.Myapps.Twitter.Service.PostService;
import com.Myapps.Twitter.dto.CreatePostDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class PostController {

    private final PostService postService;

    private final PostRepository postRepository;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse>createPosts(@RequestBody CreatePostDto createPostDto){
        return ResponseEntity.ok().body(postService.createPost(createPostDto));
    }

    @PostMapping(value = "/createMediaPost",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse>createMediaPosts(@RequestPart("createPostDto") String createPostDto,@RequestPart("media") List<MultipartFile>media) throws IOException {
        return ResponseEntity.ok().body(postService.createPostWithMedia(createPostDto,media));
    }


    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse>getAllPosts(){
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse>getPostsById(@RequestParam("postId") Integer postId){
        return ResponseEntity.ok().body(postService.getPostById(postId));

    }
    @GetMapping("/getAllByAuthor")
    public ResponseEntity<ApiResponse>getAllByAuthor(@RequestParam("authorId") Integer authorId){
        return ResponseEntity.ok().body(postService.getAllPostByAuthorId(authorId));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<ApiResponse>deleteById(@RequestParam("postId") Integer postId){
        return ResponseEntity.ok().body(postService.deletePost(postId));
    }


}
