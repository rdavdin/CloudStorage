package com.udacity.jwdnd.course1.cloudstorage.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;

@Mapper
public interface HomeMapper {
    @Insert("INSERT INTO FILES (file_name, content_type, file_size, file_data, user_id)" +
                            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer createFile(FileModel fileModel);
    
    @Select("SELECT * FROM FILES WHERE user_id = #{userId}")
    public ArrayList<FileModel> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE file_id = #{fileId} AND user_id = #{userId}")
    public FileModel getFileData(Integer fileId, Integer userId);

    @Delete("DELETE FROM FILES WHERE file_id = #{fileId} AND user_id = #{userId}")
    public Integer deleteFile(Integer fileId, Integer userId);

    @Insert("INSERT INTO NOTES (note_title, note_description, user_id)" + 
                            "VALUES(#{noteTitle}, #{noteDesc}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public Integer createNote(NoteModel noteModel);

    @Select("SELECT * FROM NOTES WHERE user_id = #{userId}")
    public ArrayList<NoteModel> getAllNotes(Integer userId);

    @Delete("DELETE FROM NOTES WHERE note_id = #{noteId} AND user_id = #{userId}")
    public Integer deleteNote(Integer noteId, Integer userId);

    @Update("UPDATE NOTES SET note_title = #{noteTitle}, note_description = #{noteDesc} WHERE note_id = #{noteId} AND user_id = #{userId}")
    public Integer updateNote(NoteModel noteModel);

    @Insert("INSERT INTO CREDENTIALS (url, username, encoded_key, password, user_id)" + 
                                "VALUES(#{url}, #{username}, #{encodedKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")                                
    public Integer createCredential(CredentialModel credentialModel);

    @Select("SELECT * FROM CREDENTIALS WHERE user_id = #{userId}")
    public ArrayList<CredentialModel> getAllCredentials(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credential_id = #{credentialId} AND user_id = #{userId}")
    public Integer deleteCredential(Integer credentialId, Integer userId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, encoded_key = #{encodedKey}, password = #{password}  WHERE credential_id = #{credentialId} AND user_id = #{userId}")
    public Integer updateCredential(CredentialModel credentialModel);
}
