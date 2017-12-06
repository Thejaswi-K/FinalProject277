package com.example.thejaswi.libraryapplication.model.entities;

import java.util.List;
import java.util.Set;

/**
 * Created by Mak on 12/5/17.
 */

public class Catalog {

    private int catalog_id;
    Set<Book> bookSet;
    private List<Patron> wait_list;
    private String title;
    private String image_url;
    private String author;
    private String isbn;
    private String publisher;
    private int call_number;
    private String year;
    private String location;
    private int number_of_copies;
    private Set<String> keywords;

    public int getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(int catalog_id) {
        this.catalog_id = catalog_id;
    }

    public Set<Book> getBookSet() {
        return bookSet;
    }

    public void setBookSet(Set<Book> bookSet) {
        this.bookSet = bookSet;
    }

    public List<Patron> getWait_list() {
        return wait_list;
    }

    public void setWait_list(List<Patron> wait_list) {
        this.wait_list = wait_list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getCall_number() {
        return call_number;
    }

    public void setCall_number(int call_number) {
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

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }
}
