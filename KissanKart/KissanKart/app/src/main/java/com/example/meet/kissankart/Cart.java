package com.example.meet.kissankart;

import android.util.Log;

/**
 * Created by KALPESH GHARTE on 1/26/2018.
 */

public class Cart
{
    private String image;
    private int price,quantity;
    private String title,id,productid;

    public Cart(int price, int quantity, String title, String image, String id, String productid) {
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.image = image;
        this.id = id;
        this.productid = productid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getPrice()
    {
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

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

