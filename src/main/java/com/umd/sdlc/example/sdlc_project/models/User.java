package com.umd.sdlc.example.sdlc_project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String password;
    public Boolean isAdmin;

    public User() {}

    public User(String name, String email, String password) {
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
