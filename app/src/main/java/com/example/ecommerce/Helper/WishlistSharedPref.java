package com.example.ecommerce.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class WishlistSharedPref {
    private static WishlistSharedPref mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "wishlist";


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
}
