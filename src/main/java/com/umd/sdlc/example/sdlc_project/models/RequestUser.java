package com.umd.sdlc.example.sdlc_project.models;

public class RequestUser {
    
    private String name;
    private String email;
    private String password;
    private Boolean isAdmin;

    public RequestUser() {}

    public RequestUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setName(String name) {this.name = name;}
    public String getName() {return this.name;}

    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return this.email;}

    public void setPassword(String password) {this.password = password;}
    public String getPassword() {return this.password;}

    public void setIsAdmin(Boolean isAdmin) {this.isAdmin = isAdmin;}
    public Boolean getIsAdmin() {return this.isAdmin;}
}
