package com.foodu.features.home.data.model;

public class FoodItem {
    public final String id;
    public final String name;
    public final String imageUrl; // có thể dùng int cho local drawable
    public final double price;

    public FoodItem(String id, String name, String imageUrl, double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
