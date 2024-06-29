package com.example.choresandshop;

public class ShopItem {
    private String name;
    private String image;
    private String price;

    public ShopItem() {
    }

    public String getName() {
        return name;
    }

    public ShopItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ShopItem setImage(String image) {
        this.image = image;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public ShopItem setPrice(String price) {
        this.price = price;
        return this;
    }
}
