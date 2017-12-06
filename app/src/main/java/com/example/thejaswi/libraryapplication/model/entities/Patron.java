package com.example.thejaswi.libraryapplication.model.entities;

import java.util.List;
import java.util.Set;

/**
 * Created by Mak on 12/3/17.
 */

public class Patron {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String university_id;

    private boolean verified;

    List<Catalog> wait_list;

    Set<Transaction> transactions;

    Set<Book> books_issued;

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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Catalog> getWait_list() {
        return wait_list;
    }

    public void setWait_list(List<Catalog> wait_list) {
        this.wait_list = wait_list;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Book> getBooks_issued() {
        return books_issued;
    }

    public void setBooks_issued(Set<Book> books_issued) {
        this.books_issued = books_issued;
    }
}
