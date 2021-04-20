package com.example.ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.MyAdapter;
import com.example.ecommerce.Adapter.ReviewAdapter;
import com.example.ecommerce.R;

public class Viewallreview extends AppCompatActivity {
    RecyclerView vertical_review;
    String[] str=new String[]{"","","","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallreview);
        vertical_review=findViewById(R.id.vertical_review);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Rating & Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        vertical_review.setLayoutManager(layoutManager);
        vertical_review.setHasFixedSize(true);
        ReviewAdapter myAdapter=new ReviewAdapter(str,getApplicationContext());
        vertical_review.setAdapter(myAdapter);
    }
}
