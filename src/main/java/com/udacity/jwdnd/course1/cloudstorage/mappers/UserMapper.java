package com.udacity.jwdnd.course1.cloudstorage.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;


@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USERS (username, salt, password, first_name, last_name) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public Integer createUser(UserModel userModel);
    
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public UserModel getUser(String username);
    
}
