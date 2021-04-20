package com.example.ecommerce.Model;

public class MyCartModel {


    String c_id, c_img,  c_name, c_price;
    double c_cut_price, c_total_amount;
    long coupan;
    int c_coupanApplied, c_count_item;

    public MyCartModel(String c_id, String c_img, String c_name, String c_price, double c_cut_price, double c_total_amount,
                       long coupan, int c_coupanApplied, int c_count_item) {
        this.c_id = c_id;
        this.c_img = c_img;
        this.c_name = c_name;
        this.c_price = c_price;
        this.c_cut_price = c_cut_price;
        this.c_total_amount = c_total_amount;
        this.coupan = coupan;
        this.c_coupanApplied = c_coupanApplied;
        this.c_count_item = c_count_item;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_price() {
        return c_price;
    }

    public void setC_price(String c_price) {
        this.c_price = c_price;
    }

    public double getC_cut_price() {
        return c_cut_price;
    }

    public void setC_cut_price(double c_cut_price) {
        this.c_cut_price = c_cut_price;
    }

    public double getC_total_amount() {
        return c_total_amount;
    }

    public void setC_total_amount(double c_total_amount) {
        this.c_total_amount = c_total_amount;
    }

    public long getCoupan() {
        return coupan;
    }

    public void setCoupan(long coupan) {
        this.coupan = coupan;
    }

    public int getC_coupanApplied() {
        return c_coupanApplied;
    }

    public void setC_coupanApplied(int c_coupanApplied) {
        this.c_coupanApplied = c_coupanApplied;
    }

    public int getC_count_item() {
        return c_count_item;
    }

    public void setC_count_item(int c_count_item) {
        this.c_count_item = c_count_item;
    }

    //    public static final int CART_ITEM =0;
//    public static final int TOTAL_AMOUNT =1;
//
//    private  int type;
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    /////cart item/////////////
//    private int produtImage,freeCoupons,offersApplied,couponsApplied;
//    private String productTitle, productPrice,cuttedPrice;
//
//    public MyCartModel(int type, int produtImage, int freeCoupons, int offersApplied,
//                       int couponsApplied, String productTitle, String productPrice, String cuttedPrice) {
//        this.type = type;
//        this.produtImage = produtImage;
//        this.freeCoupons = freeCoupons;
//        this.offersApplied = offersApplied;
//        this.couponsApplied = couponsApplied;
//        this.productTitle = productTitle;
//        this.productPrice = productPrice;
//        this.cuttedPrice = cuttedPrice;
//    }
//
//    public int getProdutImage() {
//        return produtImage;
//    }
//
//    public void setProdutImage(int produtImage) {
//        this.produtImage = produtImage;
//    }
//
//    public int getFreeCoupons() {
//        return freeCoupons;
//    }
//
//    public void setFreeCoupons(int freeCoupons) {
//        this.freeCoupons = freeCoupons;
//    }
//
//    public int getOffersApplied() {
//        return offersApplied;
//    }
//
//    public void setOffersApplied(int offersApplied) {
//        this.offersApplied = offersApplied;
//    }
//
//    public int getCouponsApplied() {
//        return couponsApplied;
//    }
//
//    public void setCouponsApplied(int couponsApplied) {
//        this.couponsApplied = couponsApplied;
//    }
//
//    public String getProductTitle() {
//        return productTitle;
//    }
//
//    public void setProductTitle(String productTitle) {
//        this.productTitle = productTitle;
//    }
//
//    public String getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(String productPrice) {
//        this.productPrice = productPrice;
//    }
//
//    public String getCuttedPrice() {
//        return cuttedPrice;
//    }
//
//    public void setCuttedPrice(String cuttedPrice) {
//        this.cuttedPrice = cuttedPrice;
//    }

    /////cart item////////////
}
