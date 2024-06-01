package com.example.assigmentthree.jwt;

import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class GenerateJWT {
    public String tokenGenerator(String email) {
        Date currentTime = new Date();
        Date expirationTime = new Date(currentTime.getTime() + 600000); // 600000 ms = 10 mins

        return Jwts.builder().setSubject(email)
                .setIssuedAt(new Date()).setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, "SecretKey").compact();

    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("SecretKey")
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey("SecretKey").parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Expired or invalid JWT token");
        }
    }
}
