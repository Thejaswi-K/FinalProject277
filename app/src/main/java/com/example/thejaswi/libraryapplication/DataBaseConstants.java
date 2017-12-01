package com.example.thejaswi.libraryapplication;

/**
 * Created by thejaswi on 11/12/2017.
 */

public class DataBaseConstants {
    static final String DB_NAME="library_db";
    static final String TB_NAME="users";
    static final int DB_VERSION='2';
    static final String CNAME = "name";
    static final String Email="email";
    static final String PASSWORD="pass";
    static final String UID="uid";

    static final String CREATE_TABLE = "CREATE TABLE " + TB_NAME +" (" + Email + " TEXT UNIQUE, " + CNAME + " TEXT, "+PASSWORD+" TEXT, "+UID+" TEXT)";
    static final String DROP_TABLE = "DROP TABLE " + TB_NAME +" IF EXISTS";
}