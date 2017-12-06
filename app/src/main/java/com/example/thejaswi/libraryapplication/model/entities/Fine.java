package com.example.thejaswi.libraryapplication.model.entities;

/**
 * Created by Mak on 12/5/17.
 */

public class Fine {

    private int amount;
    private Patron patron;
    private boolean paid;
    private Book book;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

