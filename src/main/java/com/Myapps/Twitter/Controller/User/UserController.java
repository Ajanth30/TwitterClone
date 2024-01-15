package com.Myapps.Twitter.Controller.User;

import com.Myapps.Twitter.Enum.ImageType;
import com.Myapps.Twitter.Models.RequestModels.ImageUpload;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Repository.UserRepository;
import com.Myapps.Twitter.Service.UserService;
import com.Myapps.Twitter.dto.FindUsernameDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "javainuseapi")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;


    @GetMapping("/get-current-user")
    public ResponseEntity<ApiResponse> getCurrentUser(){
        return ResponseEntity.ok().body(userService.getCurrentUser());
    }

    @PostMapping(value = "/upload-profile-img",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadProfileImage(@RequestParam("image") MultipartFile image){

        return ResponseEntity.ok().body(userService.uploadProfileOrBannerImage(ImageType.PROFILE,image));
    }

    @PostMapping(value = "/upload-banner-img",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadBannerImage(@RequestParam("image") MultipartFile image){

        return ResponseEntity.ok().body(userService.uploadProfileOrBannerImage(ImageType.BANNER,image));
    }

    @PostMapping("/follow")
    public ResponseEntity<ApiResponse> follow(@RequestParam("following_id") int followingId){
        return ResponseEntity.ok().body(userService.follow(followingId));
    }

    @GetMapping("/get-all-followers")
    public ResponseEntity<ApiResponse> getAllFollowers(){
        return ResponseEntity.ok().body(userService.getAllFollowers());
    }
    @GetMapping("/get-all-following")
    public ResponseEntity<ApiResponse> getAllFollowing(){
        return ResponseEntity.ok().body(userService.getAllFollowings());
    }

    @PostMapping("/find")
    public ResponseEntity<ApiResponse> verifyUser(FindUsernameDto credentials){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.ok().body(userService.verifyUsername(credentials));
    }

    @PostMapping("/identifiers")
    public ResponseEntity<ApiResponse> getIdentifiers(FindUsernameDto credentials){
        return ResponseEntity.ok().body(userService.getUserEmailAndPhone(credentials));
    }

}
