package com.company;
public class Fish extends AquariumCreature{
    public Fish(String name, double price) {
        super(name, price);
    }

    public Fish() {
    }

    @Override
    public String toString() {
        return "Fish{" +
                "name='" + super.getName() + '\'' +
                ", price=" + super.getPrice() +
                '}';
    }
}
