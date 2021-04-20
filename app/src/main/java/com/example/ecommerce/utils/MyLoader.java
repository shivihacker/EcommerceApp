package com.example.ecommerce.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class MyLoader {
    Context context;
     ProgressDialog progressDialog;

    public MyLoader(Context context)
    {
        this.context = context;
        progressDialog=new ProgressDialog(context);
    }

    public void progressDialog(){


        progressDialog.setMessage("Its loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        // progressDialog.setTitle("ProgressDialog bar example");
        progressDialog.show();

    }
    public void progresshide(){
        progressDialog.hide();

    }
}
