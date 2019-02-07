package com.example.meet.kissankart;

/**
 * Created by KALPESH GHARTE on 4/6/2018.
 */

public class ShowOrder {
    private int id;
    private String image, title;
    private float price;

    public ShowOrder(int id, String image, String title, float price) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
