package com.example.attestations;

public class PasswordRecord {
    private int Id;
    private int UserId;
    private String Service;
    private String Login;
    private String Password;
    private String Note;
    public PasswordRecord(){}
    public PasswordRecord(int Id,int UserId, String Service, String Login, String Password, String Note){
        this.Id = Id;
        this.UserId = UserId;
        this.Service = Service;
        this.Login = Login;
        this.Password = Password;
        this.Note = Note;
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

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
