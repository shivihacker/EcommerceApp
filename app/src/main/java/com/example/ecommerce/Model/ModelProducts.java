package com.example.ecommerce.Model;

public class ModelProducts {
    private String name, caregoryId, rating, price, date, description,title, image,brand;
    private String review;

    public ModelProducts(){

    }

    public ModelProducts( String caregoryId, String name,String rating, String price, String date, String description,String title, String image,String brand, String review) {
        this.name = name;
        this.caregoryId = caregoryId;
        this.rating = rating;
        this.price = price;
        this.date = date;
        this.description = description;
        this.title=title;
        this.image = image;
        this.brand = brand;
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaregoryId() {
        return caregoryId;
    }

    public void setCaregoryId(String caregoryId) {
        this.caregoryId = caregoryId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


}

