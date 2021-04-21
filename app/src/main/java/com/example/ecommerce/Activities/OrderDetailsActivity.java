package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.R;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView txtCashOnDelivery;
    ImageView dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        txtCashOnDelivery=findViewById(R.id.cash_on_delivery_text);
        dot=findViewById(R.id.dot);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ///////////////total amount ka text h/////////////////////
        dot.setVisibility(View.VISIBLE);
        txtCashOnDelivery.setVisibility(View.VISIBLE);
        ///////////////total amount ka text h/////////////////////

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}