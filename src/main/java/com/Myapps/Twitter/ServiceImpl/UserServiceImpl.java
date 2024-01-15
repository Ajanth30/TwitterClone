package com.Myapps.Twitter.ServiceImpl;

import com.Myapps.Twitter.Enum.ImageType;
import com.Myapps.Twitter.Exceptions.AuthExceptions.EmailAlreadyTakenException;
import com.Myapps.Twitter.Exceptions.AuthExceptions.InvalidVerificationCodeException;
import com.Myapps.Twitter.Exceptions.AuthExceptions.NoSuchUserException;
import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.RequestModels.LoginRequest;
import com.Myapps.Twitter.Models.Image.Image;
import com.Myapps.Twitter.Models.RequestModels.UpdateNumberRequest;
import com.Myapps.Twitter.Models.RequestModels.UpdatePassword;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Models.Response.AuthResponse;
import com.Myapps.Twitter.Repository.ImageRepository;
import com.Myapps.Twitter.Repository.RoleRepository;
import com.Myapps.Twitter.Repository.UserRepository;
import com.Myapps.Twitter.Models.RequestModels.RegisterRequest;
import com.Myapps.Twitter.Service.MailService;
import com.Myapps.Twitter.Service.UserService;
import com.Myapps.Twitter.Utils.Image.FileServices;
import com.Myapps.Twitter.Utils.Security.TokenServices;
import com.Myapps.Twitter.dto.FindUsernameDto;
import com.Myapps.Twitter.dto.PasswordCodeDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;

    private final ImageRepository imageRepo;
    private  final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    private  final AuthenticationManager authManager;

    private final TokenServices tokenServices;

    private final FileServices fileServices;




    @Override
    public ApiResponse registerUser(RegisterRequest user){
        ApplicationUser newUser=new ApplicationUser();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setDateOfBirth(user.getDateOfBirth());

        String name=user.getFirstName()+user.getLastName();
        boolean isNameTaken=true;
        String tempName="";
        while (isNameTaken){
            tempName=generateUserName(name);
            if(userRepo.findByUsername(tempName)==null){
                isNameTaken=false;
            }
        }
        newUser.setUsername(tempName);

        try {
            return ApiResponse.success(userRepo.save(newUser));

        }catch (Exception e){
            throw new EmailAlreadyTakenException();
        }
    }

    @Override
    public  ApiResponse login(LoginRequest request){
        try{
            Authentication auth =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            ApplicationUser user=userRepo.findByUsername(request.getUsername());
            String token=tokenServices.generateToken(user);
             return ApiResponse.success(new AuthResponse(token,user));


        }catch (AuthenticationException e){
            return ApiResponse.failure(e.getMessage());
        }


    }

    @Override
    public ApiResponse getCurrentUser() {
        String username=tokenServices.getUsernameFromToken();
        Integer userId=tokenServices.getUserIdFromToken();
        return ApiResponse.success(userRepo.findById(userId).get());
    }

    @Override
    public ApiResponse updateNumber(UpdateNumberRequest request) {
        ApplicationUser user=userRepo.findByUsername(request.getUserName());
        user.setPhone(request.getPhoneNumber());
        userRepo.save(user);
        return ApiResponse.success("true");
    }

    @Override
    public ApiResponse sendVerificationMail(String to) {
        ApplicationUser user=userRepo.findByEmail(to).orElseThrow(NoSuchUserException::new);
        long verificationCode=(long)Math.floor(Math.random()*10000);
        user.setVerificationCode(verificationCode+"");
        userRepo.save(user);
        return  mailService.sendMail(to,verificationCode);
    }

    @Override
    public ApiResponse accountVerification(String username, String otp) {
        ApplicationUser user=userRepo.findByUsername(username);
        if(user.getVerificationCode().equals(otp)){
            user.setVerificationCode("");
            userRepo.save(user);
            return ApiResponse.success(true);
        }
        else {
            throw  new InvalidVerificationCodeException();
        }
    }

    @Override
    public ApiResponse updatePassword(UpdatePassword request) {
        ApplicationUser user =userRepo.findByUsername(request.username);
        String encryptedPassword=passwordEncoder.encode(request.password);
        user.setPassword(encryptedPassword);
        userRepo.save(user);
        return ApiResponse.success(true);
    }

    @Override
    public ApiResponse uploadProfileOrBannerImage(ImageType type, MultipartFile image) {
        try{
            String userName=tokenServices.getUsernameFromToken();
            ApplicationUser user=userRepo.findByUsername(userName);

            Image img = type==ImageType.PROFILE? fileServices.UploadImage(image,"prf"):
                    fileServices.UploadImage(image,"ban");

            Image existingImage=null;

            if (type == ImageType.PROFILE) {
                existingImage=user.getProfileImage();
                user.setProfileImage(img);
            } else {
                existingImage =user.getCoverImage();
                user.setCoverImage(img);
            }
            if(existingImage!=null){
                fileServices.deleteImageByImageName(existingImage.getImageName());
                imageRepo.delete(existingImage);
            }

            userRepo.save(user);
            return ApiResponse.success(true);

        }catch (Exception e){
            return ApiResponse.failure(false);

        }
    }

    @Override
    public ApiResponse follow(int followingId) {
        int userId=tokenServices.getUserIdFromToken();
        userRepo.AddFollower(followingId,userId);
        userRepo.AddFollowing(userId,followingId);
        return ApiResponse.success(true);
    }

    @Override
    public ApiResponse getAllFollowers() {
        int userId=tokenServices.getUserIdFromToken();
        return ApiResponse.success(userRepo.getAllFollowers(userId));
    }

    @Override
    public ApiResponse getAllFollowings() {
        int userId=tokenServices.getUserIdFromToken();
        return ApiResponse.success(userRepo.getAllFollowings(userId));
    }

    @Override
    public ApiResponse verifyUsername(FindUsernameDto credentials) {
        ApplicationUser user= userRepo.findByEmailOrPhoneOrUsername(credentials.getEmail(),
                credentials.getPhone(),
                credentials.getUsername()).orElseThrow(NoSuchUserException::new);
        return ApiResponse.success(user.getUsername());
    }

    @Override
    public ApiResponse getUserEmailAndPhone(FindUsernameDto credentials) {
        ApplicationUser user=userRepo.findByEmailOrPhoneOrUsername(credentials.getEmail(),credentials.getPhone(),credentials.getUsername())
                .orElseThrow(NoSuchUserException::new);
        return ApiResponse.success(new FindUsernameDto(user.getEmail(),user.getPhone(),user.getUsername()));

    }

    @Override
    public ApiResponse sendPasswordRestCode(PasswordCodeDto passwordCodeDto) {
        return mailService.sendMail(passwordCodeDto.getEmail(),passwordCodeDto.getCode());
    }

    private String generateUserName(String name){
        long randomNumber=(long)Math.floor(Math.random()*1000000000);
        return name+randomNumber;
    }
}
