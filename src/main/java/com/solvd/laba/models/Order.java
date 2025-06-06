package com.solvd.laba.models;

import lombok.Data;

@Data
public class Order {

    private int id;

    private int userId;

    private String productName;

    private int quantity;

    private double price;
}
