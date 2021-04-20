package com.example.ecommerce.Model;

public class MyOrderItemModel {

    private int productImage;
    private String productTitle;
    private String deliveryStatus;

    public MyOrderItemModel(int productImage, String productTitle, String deliveryStatus) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.deliveryStatus = deliveryStatus;
    }

    public int getProdutImage() {
        return productImage;
    }

    public void setProdutImage(int produtImage) {
        this.productImage = produtImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
