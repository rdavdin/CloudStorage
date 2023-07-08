package com.udacity.jwdnd.course1.cloudstorage.models;

public class NoteModel {
    private Integer noteId;
    private String noteTitle;
    private String noteDesc;
    private Integer userId;
    public NoteModel(Integer noteId, String noteTitle, String noteDesc, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDesc = noteDesc;
        this.userId = userId;
    }
    public Integer getNoteId() {
        return noteId;
    }
    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }
    public String getNoteTitle() {
        return noteTitle;
    }
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }
    public String getNoteDesc() {
        return noteDesc;
    }
    public void setNoteDesc(String noteDesc) {
        this.noteDesc = noteDesc;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
