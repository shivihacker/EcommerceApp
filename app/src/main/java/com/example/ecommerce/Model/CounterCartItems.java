package com.example.ecommerce.Model;

public class CounterCartItems {
    static int counter;
    int position;
    String id;


    public CounterCartItems(int position, String id, int counter) {
        this.position = position;
        this.counter = counter;
        this.id = id;
    }

    public CounterCartItems() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
