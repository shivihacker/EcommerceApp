package com.example.ecommerce.Helper;

/**
 * Created by Hacker Abhi on 1/28/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ecommerce.Activities.LoginPage;
import com.example.ecommerce.Model.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SharedPrefManager {

//the constants
private static final String SHARED_PREF_NAME = "Ecommerce";
        private static final String KEY_USER_ID = "user_id";
        private static final String KEY_USERNAME = "user_name";
        private static final String KEY_EMAIL = "key_email";
        private static final String KEY_MOBILE = "key_mobile";
        private static final String KEY_PASS = "key_pass";
        private static final String KEY_TOTAL_AMOUNT = "key_total_amount";

        private static final String WISHLISTITEM= "key_wishlist_item";


private static SharedPrefManager mInstance;
private static Context mCtx;

public SharedPrefManager(Context context) {
        mCtx = context;
}

public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
                mInstance = new SharedPrefManager(context);
        }
        return mInstance;
}

//method to let the user login
//this method will store the user data in shared preferences
public void userLogin(String getID, String getName, String getEmail, String getMobile, String getPassword) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_ID, getID);
        editor.putString(KEY_USERNAME, getName);
        editor.putString(KEY_EMAIL,getEmail);
        editor.putString(KEY_MOBILE, getMobile);
        editor.putString(KEY_PASS, getPassword);

        editor.apply();
}

//this method will checker whether user is already logged in or not
public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
}

//this method will give the logged in user
public UserDetails getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserDetails(

                sharedPreferences.getString(KEY_USER_ID,null),
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_MOBILE, null),
                sharedPreferences.getString(KEY_PASS, null)
        );
}

                //this method will logout the user
public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
//        editor.apply();
        editor.commit();
        Intent i = new Intent(mCtx, LoginPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(i);
    }
    public void setTotalAmount(String totalAmount){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOTAL_AMOUNT, totalAmount);
        editor.apply();
    }

   public String getTotalAmount() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String value = sharedPreferences.getString("key_total_amount","");
        return value;
   }


   public void deleteItems() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("product_id");
//        editor.apply();
        editor.commit();
   }

   public void wishListItem(String product_id){
       SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putStringSet("key_wishlist_item", Collections.singleton(product_id));
       editor.apply();
   }
    public void setAddress(String key,String name, String address,String pincode){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, name);
        editor.putString(key, address);
        editor.putString(key, pincode);
        editor.apply();
        Log.d("addressData",""+getAddress(key));
    }

    public String getAddress(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");
        return value;
    }

    public void setProductPath(String p_collection, String p_doc,String sub_p_collection,String sub_p_doc,String super_sub_p_collection){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("p_collection", p_collection);
        editor.putString("p_doc", p_doc);
        editor.putString("sub_p_collection", sub_p_collection);
        editor.putString("sub_p_doc", sub_p_doc);
        editor.putString("super_sub_p_collection", super_sub_p_collection);
        editor.apply();
        Log.d("WishlistSharedData"," "+p_doc+" "+p_collection);
    }
    public void setMoreHomeProduct(String p_collection, String p_doc,String sub_p_collection){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("p_collection", p_collection);
        editor.putString("p_doc", p_doc);
        editor.putString("sub_p_collection", sub_p_collection);
        editor.apply();
        Log.d("WishlistSharedData"," "+p_doc+" "+p_collection);
    }
    public String get_p_doc(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");

        return value;
    }
    public String get_p_collection(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");

        return value;
    }
    public String get_sub_p_doc(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");

        return value;
    }
    public String get_sub_p_collection(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");

        return value;
    }
    public String get_super_sub_p_collection(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,"");

        return value;
    }
}


