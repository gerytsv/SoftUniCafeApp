
package com.example.android.softunicafeapp.data;

public class User {

    public String email;
    public String name;
    public String surName;
    public String phone;
    public String password;
/*
    public User(String email, String name, String surName, String phone, String password){
        this.email = email;
        this.password = password;
    } */

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

