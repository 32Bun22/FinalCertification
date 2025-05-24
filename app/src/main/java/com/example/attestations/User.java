package com.example.attestations;

public class User {
    private int Id;
    private String Login;
    private String Password;
    public User(){}

    public User(int Id,String Login, String Password){
        this.Id = Id;
        this.Login = Login;
        this.Password = Password;
    }

    public int getId() {
        return Id;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
