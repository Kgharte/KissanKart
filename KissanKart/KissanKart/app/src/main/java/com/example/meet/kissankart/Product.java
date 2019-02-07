package com.example.meet.kissankart;

/**
 * Created by KALPESH GHARTE on 12/20/2017.
 */

class Product
{
private int id,categoryid,quantity;
private float price;
private String title,image,weight;
private Boolean status;

    public Product()
    {

    }

    public Product(int id, int categoryid, int quantity, int price, String title, String image, String weight, Boolean status) {
        this.id = id;
        this.categoryid = categoryid;
        this.quantity = quantity;
        this.price = price;
        this.title = title;
        this.image = image;
        this.weight = weight;
        this.status = status;
    }

    public Product(int id, int price, int quantity, String weight, boolean status, String title, String image) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return (int) price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
