package com.umd.sdlc.example.sdlc_project.models;

public class Account {
    
    private Long id;

    private Long userId;

    private String accountNumber;

    private Float amount;

    public Account(){}

    public void setId(Long id) {this.id = id;}
    public Long getId() {return this.id;}

    public void setUserId(Long id) {this.userId = id;}
    public Long getUserId() {return this.userId;}

    public void setAccountNumber(String account) {this.accountNumber = account;}
    public String getAccountNumber() {return this.accountNumber;}

    public void setAmount(Float amount) {this.amount = amount;}
    public Float getAmount() {return this.amount;}
}
