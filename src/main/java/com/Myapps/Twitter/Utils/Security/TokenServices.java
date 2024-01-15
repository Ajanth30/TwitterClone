package com.Myapps.Twitter.Utils.Security;

import com.Myapps.Twitter.Models.Auth.ApplicationUser;
import com.Myapps.Twitter.Models.Auth.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenServices {

    private final   JwtEncoder encoder;
    private final   JwtDecoder decoder;
    private final   HttpServletRequest request;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;


    public String generateToken(ApplicationUser user){

        Instant currentTime=Instant.now();


        String scope=user.getAuthorities().stream().map(Role::getAuthority)
                .collect(Collectors.joining(" "));

        Consumer<Map<String,Object>> customClaims=map->{
            map.put("userId",Integer.toString(user.getUserId()));
            map.put("email",user.getEmail());
            map.put("scopes",scope);
        };
        JwtClaimsSet claims= JwtClaimsSet.builder()
                .issuer("Twitter")
                .issuedAt(Instant.now())
                .subject(user.getUsername())
                .claims(customClaims)
                .expiresAt(Instant.now().plus(jwtExpirationMs, ChronoUnit.MILLIS))
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();


    }

    public String getUsernameFromToken(){
        String token=getTokenFromHeader(request);
        Jwt decodedToken=decoder.decode(token);
        return decodedToken.getSubject();
    }

    public int getUserIdFromToken(){
        String token=getTokenFromHeader(request);
        Jwt decodedToken=decoder.decode(token);
        return Integer.parseInt((String)decodedToken.getClaims().get("userId"));

    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public String getTokenFromHeader(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        return extractBearerToken(header);
    }



}
