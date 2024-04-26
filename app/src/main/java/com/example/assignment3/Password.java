package com.example.assignment3;

public class Password {
    private String username, password, url;
    private int id, userid;

    public Password() {

    }

    public Password(int id, int userid, String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.id = id;
        this.userid = userid;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
