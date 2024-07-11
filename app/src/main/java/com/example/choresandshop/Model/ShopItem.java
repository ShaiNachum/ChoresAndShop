package com.example.choresandshop.Model;

public class ShopItem {
    private String name;
    private String image;
    private int price;
    private boolean purchased;
    private String purchasedBy;

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

    public int getPrice() {
        return price;
    }

    public ShopItem setPrice(int price) {
        this.price = price;
        return this;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public ShopItem setPurchased(boolean purchased) {
        this.purchased = purchased;
        return this;
    }

    public String getPurchasedBy() {
        return purchasedBy;
    }

    public ShopItem setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
        return this;
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
