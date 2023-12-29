package com.example.buoi7;

public class ToDo {
    public int ID ;
    public String title ;
    public String content ;
    public String date ;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ToDo(int ID, String title, String content, String date) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.date = date;
    }
    public ToDo() {
    }
}
