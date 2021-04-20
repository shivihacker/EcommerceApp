package com.example.ecommerce.Model;

import java.util.ArrayList;

public class SliderModel {
    private String banner;
   // String url;

    public SliderModel(String banner) {
        this.banner = banner;
    }
    public SliderModel() {

    }


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
