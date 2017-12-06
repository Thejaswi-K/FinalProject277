package com.example.thejaswi.libraryapplication.model.entities;

/**
 * Created by Mak on 12/3/17.
 */

public class Login {
    private String email;
    private String password;
    private boolean verified;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

