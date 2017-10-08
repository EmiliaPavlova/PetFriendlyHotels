package com.example.emily.petfriendlyhotels;

public class Hotel {
    public String id;
    public String name;
    public String address;
    public String rating;
    public String[] location;

    public Hotel(String id, String name, String address, String rating, String[] location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.location = location;
    }
}
