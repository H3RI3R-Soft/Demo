package com.test.studentCRUD.Configuration;

import io.jsonwebtoken.Claims;
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


    public String generateToken (String email,String name){
        return Jwts.builder()
                .setSubject(email)
                .claim("name",name)
                .claim("email",email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token){
        return getClaims(token).getSubject();
    }

    public String getNameFromToken(String token){
        return getClaims(token).get("name",String.class);
    }




}
