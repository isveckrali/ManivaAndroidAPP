package com.example.manivaandroapp.controller.models;

public class UserInfo {
    String mail, pass, name , username, uID;

    public UserInfo(String mail, String pass, String name, String username, String uID) {
        this.mail = mail;
        this.pass = pass;
        this.name = name;
        this.username = username;
        this.uID = uID;
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

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
