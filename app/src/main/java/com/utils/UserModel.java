package com.utils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String uid;
    private String name;
    private String email;
    private String phone_number;
    private String dateofbirth;
    private String password;

    public UserModel() {
    }

    public UserModel(String uid, String name, String email, String phone_number, String dateofbirth, String password) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.dateofbirth = dateofbirth;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", dateofbirth='" + dateofbirth + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}