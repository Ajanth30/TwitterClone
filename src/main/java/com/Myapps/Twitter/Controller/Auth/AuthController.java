package com.Myapps.Twitter.Controller.Auth;

import com.Myapps.Twitter.Models.RequestModels.LoginRequest;
import com.Myapps.Twitter.Models.RequestModels.UpdateNumberRequest;
import com.Myapps.Twitter.Models.RequestModels.UpdatePassword;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import com.Myapps.Twitter.Models.RequestModels.RegisterRequest;
import com.Myapps.Twitter.Service.UserService;
import com.Myapps.Twitter.dto.PasswordCodeDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication",description = "Authentication endpoints")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterRequest request)
    {
        return  ResponseEntity.ok().body(userService.registerUser(request));
    }

    @PostMapping("/login")
    public  ResponseEntity<ApiResponse>login(LoginRequest request){
        return ResponseEntity.ok().body(userService.login(request));
    }

    @PutMapping("/update/phoneNumber")
    public  ResponseEntity<ApiResponse>updateNumber(@RequestBody UpdateNumberRequest request){
        return ResponseEntity.ok().body(userService.updateNumber(request));
    }

    @PostMapping("/emailConformation")
    public ResponseEntity<ApiResponse> sendMail(@RequestParam("to") String to)
    {
        return  ResponseEntity.ok().body(userService.sendVerificationMail(to));
    }

    @PostMapping("/accountVerification")
    public ResponseEntity<ApiResponse> accountVerification(@RequestParam("username") String username,@RequestParam("otp") String otp)
    {
        return  ResponseEntity.ok().body(userService.accountVerification(username,otp));
    }

    @PostMapping("/update/password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody UpdatePassword request)
    {
        return  ResponseEntity.ok().body(userService.updatePassword(request));
    }

    @PostMapping("/send-passwordResetCode")
    public ResponseEntity<ApiResponse>sendPasswordResetCode(PasswordCodeDto passwordCodeDto){
        return ResponseEntity.ok().body(userService.sendPasswordRestCode(passwordCodeDto));
    }





}
