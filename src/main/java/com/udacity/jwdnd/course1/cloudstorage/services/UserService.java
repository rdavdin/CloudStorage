package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;

    public boolean isUserExists(String username){
        UserModel userModel = userMapper.getUser(username);
        return userModel != null;
    }

    public boolean createUser(UserModel userModel){
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPw = hashService.getHashedValue(userModel.getPassword(), encodedSalt);
    
            UserModel user = new UserModel(null, userModel.getUsername(), encodedSalt, hashedPw, userModel.getFirstName(), userModel.getLastName());
            Integer rs = userMapper.createUser(user);
            return rs != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getUserId(String username){
        UserModel userModel = userMapper.getUser(username);
        if(userModel != null){
            return userModel.getUserId();
        }
        return -1;
    }
}
