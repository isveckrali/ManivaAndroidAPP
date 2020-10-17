package com.example.manivaandroapp.models;

public class UserInfoModel {
    String mail, pass, name , username;

    public UserInfoModel() {
    }

    public UserInfoModel(String mail, String pass, String name, String username) {
        this.mail = mail;
        this.pass = pass;
        this.name = name;
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
