package com.example.asm1.Model;

import com.google.gson.annotations.SerializedName;

public class ProductModel{

    @SerializedName("_id")
    private String id;
    private String name;
    private int price;
    private String color;
    private String img;
    private int quantity;
    private String description;


    public ProductModel() {
    }

    public ProductModel(String id, String name, int price, String color, String img, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.img = img;
        this.quantity = quantity;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
