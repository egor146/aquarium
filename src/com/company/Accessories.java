package com.company;

public enum Accessories {
    SUBSTRATE(20, "substrate"),
    FILTER(20, "filter"),
    AIR_PUMP(20, "air pump");

    public final double price;
    public final String name;
    Accessories(double price, String name) {
        this.price = price;
        this.name = name;
    }
}
