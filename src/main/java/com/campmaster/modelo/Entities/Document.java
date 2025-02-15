package com.campmaster.modelo.Entities;

import java.sql.Date;

public class Document {
    String ID;
    String name;
    String url;
    Date date;
    String ID_Usuario;


    public Document() {
        this.name = "";
        this.url = "";
        this.date = new Date(System.currentTimeMillis());
    }

    public Document(String name, String url, Date date, String ID_Usuario) {
        this.name = name;
        this.url = url;
        this.date = date;
        this.ID_Usuario = ID_Usuario;
    }

    public Document(String ID, String name, String url, Date date, String ID_Usuario) {
        this.ID = ID;
        this.name = name;
        this.url = url;
        this.date = date;
        this.ID_Usuario = ID_Usuario;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getID_Usuario() {
        return ID_Usuario;
    }

}

