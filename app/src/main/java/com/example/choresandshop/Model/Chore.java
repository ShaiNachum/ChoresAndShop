package com.example.choresandshop.Model;

public class Chore {

    private String id;
    private String name;
    private int price;
    private boolean done;


    public Chore() {
    }

    public String getId() {
        return id;
    }

    public Chore setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }


    public Chore setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Chore setPrice(int price) {
        this.price = price;
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public Chore setDone(boolean done) {
        this.done = done;
        return this;
    }
}
