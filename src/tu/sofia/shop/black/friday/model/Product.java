package tu.sofia.shop.black.friday.model;

import tu.sofia.shop.black.friday.util.DatabaseControlUnit;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Product {
    // Keep it as a simple object → fields, constructors, getters & setters. If needed, override hashCode(), equals() and toString().
    // Extract the functional methods in service - see ProductService.java
    private int id;
    private String name;
    private double price;
    private double priceMin;
    private int quantity;
    private enum status{
        BlackFridayON,
        BlackFridayOFF
    }
    private double BlackFridayDiscount;
    Scanner sc = new Scanner (System.in);


    public Product(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(double priceMin) {
        this.priceMin = priceMin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBlackFridayDiscount() {
        return BlackFridayDiscount;
    }

    public void setBlackFridayDiscount(double blackFridayDiscount) {
        BlackFridayDiscount = blackFridayDiscount;
    }
}
