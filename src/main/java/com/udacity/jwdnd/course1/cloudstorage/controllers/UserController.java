package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
    
    @GetMapping("/signup")
    public String getSignup(@ModelAttribute UserModel userModel, Model model){
        String errorMessage = "";
        
        model.addAttribute("isSuccessful", false);
        model.addAttribute("errorMessage", errorMessage);
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute UserModel userModel, Model model){
        boolean isSuccessful = userService.createUser(userModel);
        String errorMessage = "";
        if(!isSuccessful){
            errorMessage = "There is some error!!! Plz check and try again.";
        }
        
        model.addAttribute("isSuccessful", isSuccessful);
        model.addAttribute("errorMessage", errorMessage);

        return "signup";
    }
}
