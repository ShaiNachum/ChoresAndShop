package com.example.choresandshop.Model;

public class ObjectId {
    private String superapp;
    private String id;


    public ObjectId() {
    }

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ObjectIdBoundary [superapp=" + superapp + ", id=" + id + "]";
    }

}


