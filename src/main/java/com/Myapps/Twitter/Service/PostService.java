package com.Myapps.Twitter.Service;


import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Posts.Posts;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.dto.CreatePostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface PostService {

    ApiResponse createPost(CreatePostDto createPostDto);

    ApiResponse createPostWithMedia(String createPostDto,List<MultipartFile> media) throws IOException;

    ApiResponse getAllPostByAuthorId(Integer authorId);

    ApiResponse getPostById(Integer postId);

    ApiResponse getAllPosts();

    ApiResponse deletePost(Integer postId);
}
