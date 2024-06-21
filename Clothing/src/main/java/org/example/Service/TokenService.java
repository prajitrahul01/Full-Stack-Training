package org.example.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class TokenService {


    // Secret key used for token creation and verification
    public static final String token_secret = "qwertyjnfewnsdkjwnefkjwnkjn";

    // Function to create a token for a given user ID
    public String createTokenFunction(Integer id){
        try{
            Algorithm algoObject = Algorithm.HMAC256(token_secret);
            // Create a token with user ID and creation timestamp claims
            String token = JWT.create().withClaim("userId", id.toString())
                    .withClaim("createdAt", new Date()).sign(algoObject);
            return token;
        }
        // Handle exceptions related to encoding or JWT creation
        catch(UnsupportedEncodingException | JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    // Function to extract user ID from a given token
    public String getUserIdToken(String token){
        try{
            Algorithm algo = Algorithm.HMAC256(token_secret);
            JWTVerifier jwtVerifier = JWT.require(algo).build();

            // Verify the token and retrieve the user ID claim
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return decodedJWT.getClaim("userId").asString();
        }
        catch(UnsupportedEncodingException e){
            // Handle exceptions related to encoding
            e.printStackTrace();
        }
        return null;
    }

    // Function to check if a token is valid
    public boolean isTokenValid(String token){
        // Extract user ID from the token
        String userId = this.getUserIdToken(token);
        // Check if user ID is not null, indicating a valid token
        return userId != null;
    }
}
