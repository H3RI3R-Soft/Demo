package com.test.studentCRUD.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    private final String secretKey = "infiuwefubufq9ubf9h340f3ifnionf2i3fi";
    //now as the secret key doen done after this we have to create the key
    private final SecretKey  key =  Keys.hmacShaKeyFor(secretKey.getBytes());


    public String generateToken (Authentication authentication){
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(key)
                .compact();
    }



}
