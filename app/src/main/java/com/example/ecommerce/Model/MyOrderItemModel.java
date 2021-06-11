package com.example.ecommerce.Model;

public class MyOrderItemModel {

    private String c_id,productImage;
    private String productTitle;
   // private String deliveryStatus;

    public MyOrderItemModel(String c_id,String productImage, String productTitle) {
        this.c_id = c_id;
        this.productImage = productImage;
        this.productTitle = productTitle;
     //   this.deliveryStatus = deliveryStatus;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getProdutImage() {
        return productImage;
    }

    public void setProdutImage(String produtImage) {
        this.productImage = produtImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

//    public String getDeliveryStatus() {
//        return deliveryStatus;
//    }
//
//    public void setDeliveryStatus(String deliveryStatus) {
//        this.deliveryStatus = deliveryStatus;
//    }
}
