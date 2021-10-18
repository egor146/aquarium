package com.company;
public class AquariumCreature {
    private String name;
    private double price;

    public AquariumCreature(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public AquariumCreature(){}

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AquariumCreature{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
