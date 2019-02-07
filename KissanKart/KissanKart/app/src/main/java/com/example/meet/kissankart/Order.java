package com.example.meet.kissankart;

import java.util.Date;

/**
 * Created by KALPESH GHARTE on 4/3/2018.
 */

public class Order
{
    private int id,amount;
    private String orderdate,fullname;

    public Order(int id, int amount, String orderdate, String fullname) {
        this.id = id;
        this.amount = amount;
        this.orderdate = orderdate;
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

