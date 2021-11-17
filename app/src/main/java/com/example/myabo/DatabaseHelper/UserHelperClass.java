package com.example.myabo.DatabaseHelper;

public class UserHelperClass {
    String userId;
    String name, nric, email, phoneNum, password, cfmPassword ;

    //constructor
    public UserHelperClass() {
    }

    public UserHelperClass(String userId, String name, String nric, String email, String phoneNum, String password, String cfmPassword) {
        this.userId = userId;
        this.name = name;
        this.nric = nric;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
        this.cfmPassword = cfmPassword;

    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getCfmPassword() {
        return cfmPassword;
    }

    public void setCfmPassword(String cfmPassword) {
        this.cfmPassword = cfmPassword;
    }
}
