package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;

@Service
public class AuthenticationService implements AuthenticationProvider{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserModel userModel = userMapper.getUser(username);
        if(userModel != null){
            String encodedSalt = userModel.getSalt();
            String hashedPw = hashService.getHashedValue(password, encodedSalt);
            if(userModel.getPassword().equals(hashedPw)){
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        
        return null;
        // return new UsernamePasswordAuthenticationToken("a", "1", new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
