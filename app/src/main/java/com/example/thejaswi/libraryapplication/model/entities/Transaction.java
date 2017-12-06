package com.example.thejaswi.libraryapplication.model.entities;

import java.util.Date;

/**
 * Created by Mak on 12/5/17.
 */

public class Transaction {

    private int transaction_id;
    private Patron patron;
    private Date date_issued;
    private Date date_returned;
    private Date due_date;
    private boolean renewal;
    private int renew_count;

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Date getDate_issued() {
        return date_issued;
    }

    public void setDate_issued(Date date_issued) {
        this.date_issued = date_issued;
    }

    public Date getDate_returned() {
        return date_returned;
    }

    public void setDate_returned(Date date_returned) {
        this.date_returned = date_returned;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public boolean isRenewal() {
        return renewal;
    }

    public void setRenewal(boolean renewal) {
        this.renewal = renewal;
    }

    public int getRenew_count() {
        return renew_count;
    }

    public void setRenew_count(int renew_count) {
        this.renew_count = renew_count;
    }
}
