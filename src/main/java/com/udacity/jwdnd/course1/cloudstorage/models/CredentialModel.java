package com.udacity.jwdnd.course1.cloudstorage.models;

public class CredentialModel {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private String encodedKey;
    private Integer userId;

    private String viewPw;

    public CredentialModel(Integer credentialId, String url, String username, String encodedKey, String password, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
        this.encodedKey = encodedKey;
        this.userId = userId;
    }
    public Integer getCredentialId() {
        return credentialId;
    }
    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEncodedKey() {
        return encodedKey;
    }
    public void setEncodedKey(String encodedKey) {
        this.encodedKey = encodedKey;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getViewPw() {
        return viewPw;
    }
    public void setViewPw(String viewPw) {
        this.viewPw = viewPw;
    }
}
