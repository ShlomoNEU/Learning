package com.shne.learning.Mudole;

import java.util.Date;

/**
 * Created by Shlomo on 11/02/2017.
 */

public class Transactions {

    private double amount;
    private Date date;
    private String Category;
    private String Name;

    public Transactions() {

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
