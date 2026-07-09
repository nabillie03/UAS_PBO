package com.mycompany.infentoryapp.model;

public class User {
    private int idUser;
    private String username;
    private String password;
    private String level;

    public User() {}

    public User(int idUser, String username, String password, String level) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}
