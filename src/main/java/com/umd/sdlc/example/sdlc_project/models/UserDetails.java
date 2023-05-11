package com.umd.sdlc.example.sdlc_project.models;

public class UserDetails {
    private Long id;
    private Long userId;
    private Integer age;
    private String birthday;
    private String address;

    public UserDetails() {}

    public void setId(Long id) {this.id = id;}
    public Long getId() {return this.id;}

    public void setUserId(Long userId) {this.userId = userId;}
    public Long getuserId() {return this.userId;}

    public void setAge(Integer age) {this.age = age;}
    public Integer getAge() {return this.age;}

    public void setBirthday(String birthday) {this.birthday = birthday;}
    public String getBirthday() {return this.birthday;}

    public void setAddress(String address) {this.address = address;}
    public String getAddress() {return this.address;}
}
