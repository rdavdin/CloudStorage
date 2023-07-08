package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Authentication authentication, Model model){
        Integer userId = userService.getUserId(authentication.getName());
        ArrayList<FileModel> files = homeService.getFiles(userId);
        model.addAttribute("files", files);

        ArrayList<NoteModel> notes = homeService.getNotes(userId);
        model.addAttribute("notes", notes);

        ArrayList<CredentialModel> credentials = homeService.getCredentials(userId);
        model.addAttribute("credentials", credentials);

        return "home";
    }

    @PostMapping("/home/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model){
        try {
            Integer userId = userService.getUserId(authentication.getName());
            homeService.createFile(userId, fileUpload);
            return "redirect:/home";
        } catch (Exception e) {
            return "redirect:/home?error";
        }
    }

    @GetMapping("/home/file/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id, Authentication authentication, Model model){
        try {
            Integer userId = userService.getUserId(authentication.getName());
            FileModel fileModel = homeService.getFileData(id, userId);
            
            if(fileModel == null){
                String a = "Error 404. \n The file does not exist \n";
                ByteArrayResource resource = new ByteArrayResource(a.getBytes(StandardCharsets.UTF_8));
                return ResponseEntity.badRequest()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(resource);
            }else{
                ByteArrayResource resource = new ByteArrayResource(fileModel.getFileData());
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileModel.getFileName())
                    .contentType(MediaType.parseMediaType(fileModel.getContentType()))
                    .contentLength(Long.parseLong(fileModel.getFileSize()))
                    .body(resource);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/home/file/delete/{id}")
    public String deleteFile(@PathVariable("id") int id, Authentication authentication, Model model){
        try {
            Integer userId = userService.getUserId(authentication.getName());
            Integer rt = homeService.deleteFile(id, userId);
            return rt <= 0 ? "redirect:/home?error" : "redirect:/home";
        } catch (Exception e) {
            return "redirect:/home?error";
        }
    }

    @GetMapping("/home/note/delete/{id}")
    public String deleteNote(@PathVariable("id") int id, Authentication authentication, Model model){
        try {
            Integer userId = userService.getUserId(authentication.getName());
            Integer rt = homeService.deleteNote(id, userId);
            return rt <= 0 ? "redirect:/home?error" : "redirect:/home";
        } catch (Exception e) {
            return "redirect:/home?error";
        }
    }

    @PostMapping("/home/note/update")
    public String updateNote(@ModelAttribute("noteModel") NoteModel noteModel, Authentication authentication, Model model){
        try {
            Integer userId = userService.getUserId(authentication.getName());
            Integer rt = homeService.createNote(userId, noteModel);
            return rt <= 0 ? "redirect:/home?error" : "redirect:/home";
        } catch (Exception e) {
            return "redirect:/home?error";
        }
    }

    @PostMapping("/home/credential/update")
    public String updateCredential(@ModelAttribute("credentialModel") CredentialModel credentialModel, Authentication authentication, Model model){
        try {
            Integer userId = userService.getUserId(authentication.getName());
            Integer rt = homeService.createCredential(userId, credentialModel);
            return rt <= 0 ? "redirect:/home?error" : "redirect:/home";
        } catch (Exception e) {
            return "redirect:/home?error";
        }
    }
}
