package com.Myapps.Twitter.Service;

import com.Myapps.Twitter.Enum.ImageType;
import com.Myapps.Twitter.Models.RequestModels.LoginRequest;
import com.Myapps.Twitter.Models.RequestModels.UpdateNumberRequest;
import com.Myapps.Twitter.Models.RequestModels.UpdatePassword;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Models.RequestModels.RegisterRequest;
import com.Myapps.Twitter.dto.FindUsernameDto;
import com.Myapps.Twitter.dto.PasswordCodeDto;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    public ApiResponse registerUser(RegisterRequest user);
    public ApiResponse login(LoginRequest request);

    public ApiResponse getCurrentUser();
    public  ApiResponse updateNumber(UpdateNumberRequest request);

    public ApiResponse sendVerificationMail(String to);

    public  ApiResponse accountVerification(String username,String otp);

    public ApiResponse updatePassword(UpdatePassword request);

    public ApiResponse uploadProfileOrBannerImage(ImageType type, MultipartFile image);

    public ApiResponse follow(int followingId);

    public ApiResponse getAllFollowers();

    public  ApiResponse getAllFollowings();

    public ApiResponse verifyUsername(FindUsernameDto credentials);

    public ApiResponse getUserEmailAndPhone(FindUsernameDto credentials);

    public ApiResponse sendPasswordRestCode(PasswordCodeDto passwordCodeDto);
}
