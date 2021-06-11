package com.example.ecommerce.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class WishlistSharedPref {
    private static WishlistSharedPref mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "wishlist";

    private static final String ADDRESS = "address";
    private static final String PINCODE = "pinarea";
    private static final String PHONE = "phone";

    public WishlistSharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized WishlistSharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WishlistSharedPref(context);
        }
        return mInstance;
    }

    public void setWishlist(String key, String wishlistValue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, wishlistValue);
        editor.apply();
        Log.d("WishlistSharedData",""+getWishlist(key));
    }

    public String getWishlist(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");

        return value;
    }


    public void deletewishlist(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
//        editor.apply();
        editor.commit();
    }


    public void setPoductCount(String key, String wishlistValue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, wishlistValue);


        editor.apply();
        Log.d("WishlistSharedData",""+getWishlist(key));

    }

    public String getPorductCount(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");

        return value;
    }
    public void setAddress(String address,String pincode, String phone){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ADDRESS, address);
        editor.putString(PINCODE, pincode);
        editor.putString(PHONE, phone);
        editor.apply();
        Log.d("WishlistSharedData"," "+phone+" "+address);
    }
    public String getAddress(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(ADDRESS,"");

        return value;
    }
//    public String getPhone(String key) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String value = sharedPreferences.getString(key,"");
//
//        return value;
//    }
    public String getPhone(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(PHONE,"");

        return value;
    }
    public String getPincode(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(PINCODE,"");

        return value;
    }

}
