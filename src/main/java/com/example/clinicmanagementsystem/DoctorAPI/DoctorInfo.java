package com.example.clinicmanagementsystem.DoctorAPI;

public class DoctorInfo {
    String name, email, phoneNo, specialize,password, token;

    public DoctorInfo(String name, String email, String phoneNo, String specialize, String password, String token) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.specialize = specialize;
        this.password = password;
        this.token = token;
    }

    public DoctorInfo() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSpecialize() {
        return specialize;
    }

    public void setSpecialize(String specialize) {
        this.specialize = specialize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
