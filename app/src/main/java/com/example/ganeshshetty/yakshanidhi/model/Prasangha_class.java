package com.example.ganeshshetty.yakshanidhi.model;

import java.io.Serializable;

/**
 * Created by Ganesh Shetty on 15-02-2017.
 */

public class Prasangha_class implements Serializable{
    String name,author;
    int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
