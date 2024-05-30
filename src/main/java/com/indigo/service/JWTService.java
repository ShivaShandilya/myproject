package com.indigo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.indigo.entity.PropertyUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmkey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.duration}")
    private int expiryTime;
    private Algorithm algorithm;
    private final static String USER_NAME="username";
    @PostConstruct
    public void postConstuct(){

        algorithm = algorithm.HMAC256(algorithmkey);
    }
    public String generateToken( PropertyUser propertyUser){
       return JWT.create().withClaim(USER_NAME,propertyUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUserName(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
