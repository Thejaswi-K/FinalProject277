package com.example.thejaswi.libraryapplication.model.entities;

/**
 * Created by Mak on 12/3/17.
 */

public class Librarian {


    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String university_id;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUniversty_id() {
        return university_id;
    }

    public void setUniversty_id(String universty_id) {
        this.university_id = universty_id;
    }
}
