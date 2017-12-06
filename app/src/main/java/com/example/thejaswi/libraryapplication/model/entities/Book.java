package com.example.thejaswi.libraryapplication.model.entities;

import java.util.List;

/**
 * Created by Mak on 12/5/17.
 */

public class Book {


    private int book_id;
    private Status status;
    private List<Fine> fine;
    private Patron issuer;
    private Catalog catalog;
    private List<Transaction> transaction;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Fine> getFine() {
        return fine;
    }

    public void setFine(List<Fine> fine) {
        this.fine = fine;
    }

    public Patron getIssuer() {
        return issuer;
    }

    public void setIssuer(Patron issuer) {
        this.issuer = issuer;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
}
