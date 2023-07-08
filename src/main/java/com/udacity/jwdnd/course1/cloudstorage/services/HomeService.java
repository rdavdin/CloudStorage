package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mappers.HomeMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;

@Service
public class HomeService {
    @Autowired
    HomeMapper homeMapper; 

    @Autowired
    EncryptionService encryptionService;

    public Integer createFile(Integer userId, MultipartFile file){
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            String fileSize = file.getSize()+"";
            byte[] fileData = file.getBytes();
            FileModel fileModel = new FileModel(null, fileName, contentType, fileSize, userId, fileData);
            return homeMapper.createFile(fileModel);
        } catch (Exception e) {
            return -1;
        }
    }
    
    public ArrayList<FileModel> getFiles(Integer userId){
        return homeMapper.getAllFiles(userId);
    }

    public FileModel getFileData(Integer fileId, Integer userId){
        return homeMapper.getFileData(fileId, userId);
    }

    public Integer deleteFile(Integer fileId, Integer userId){
        return homeMapper.deleteFile(fileId, userId);
    }

    public Integer createNote(Integer userId, NoteModel noteModel){
        if(noteModel.getNoteId() == null){
            return homeMapper.createNote(new NoteModel(null, noteModel.getNoteTitle(), noteModel.getNoteDesc(), userId));
        }else{
            return homeMapper.updateNote(new NoteModel(noteModel.getNoteId(), noteModel.getNoteTitle(), noteModel.getNoteDesc(), userId));
        }
    }

    public ArrayList<NoteModel> getNotes(Integer userId){
        return homeMapper.getAllNotes(userId);
    }

    public Integer deleteNote(Integer noteId, Integer userId){
        return homeMapper.deleteNote(noteId, userId);
    }

    public Integer createCredential(Integer userId, CredentialModel credModel){
        String encodedKey = generateEncodedKey();
        String password = encryptionService.encryptValue(credModel.getPassword(), encodedKey);
        
        if(credModel.getCredentialId() == null){
            CredentialModel c = new CredentialModel(null, credModel.getUrl(), credModel.getUsername(), encodedKey, password, userId);
            return homeMapper.createCredential(c);
        }else{
            CredentialModel c = new CredentialModel(credModel.getCredentialId(), credModel.getUrl(), credModel.getUsername(), encodedKey, password, userId);
            return homeMapper.updateCredential(c);
        }
    }

    public ArrayList<CredentialModel> getCredentials(Integer userId){
        ArrayList<CredentialModel> credentials = homeMapper.getAllCredentials(userId);
        for (CredentialModel credential : credentials) {
            String viewPw = encryptionService.decryptValue(credential.getPassword(), credential.getEncodedKey());
            credential.setViewPw(viewPw);
        }
        return credentials;
    }

    private String generateEncodedKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }
}
