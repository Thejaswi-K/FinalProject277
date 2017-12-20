package com.example.thejaswi.libraryapplication.model.entities;

public class BookIssuedInfo {
    private Status status;
    private String date_issued;
    private String due_date;


    private int book_id;
    private int catalog_id;
    private int transaction_id;

    private String title;

    private String author;

    private String image_url;

    private String isbn;

    private String publisher;

    private long call_number;

    private String year;

    private String location;

    private int number_of_copies;

    private String category;

    public BookIssuedInfo(Status status, String date_issued, String due_date, String title, String author, String image_url, String isbn, String publisher, long call_number, String year, String location, int number_of_copies, String category) {
        this.status = status;
        this.date_issued = date_issued;
        this.due_date = due_date;
        this.title = title;
        this.author = author;
        this.image_url = image_url;
        this.isbn = isbn;
        this.publisher = publisher;
        this.call_number = call_number;
        this.year = year;
        this.location = location;
        this.number_of_copies = number_of_copies;
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDate_issued() {
        return date_issued;
    }

    public void setDate_issued(String date_issued) {
        this.date_issued = date_issued;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public long getCall_number() {
        return call_number;
    }

    public void setCall_number(long call_number) {
        this.call_number = call_number;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumber_of_copies() {
        return number_of_copies;
    }

    public void setNumber_of_copies(int number_of_copies) {
        this.number_of_copies = number_of_copies;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(int catalog_id) {
        this.catalog_id = catalog_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }
}
