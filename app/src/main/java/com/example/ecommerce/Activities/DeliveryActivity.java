package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.MyWishlistAdapter;
import com.example.ecommerce.Model.MyWishlistModel;
import com.example.ecommerce.R;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerview;
    private Button changeOrAddNewAddressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        //anurag the warrior shivani the chudail

        deliveryRecyclerview=findViewById(R.id.delivery_recyclerview);
        changeOrAddNewAddressBtn=findViewById(R.id.change_or_add_new_adddress);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerview.setLayoutManager(linearLayoutManager);

        List<MyWishlistModel> wishlistModelList=new ArrayList<>();
//        wishlistModelList.add(new MyWishlistModel(R.drawable.realme,"Real Me",1,"3",145,"Rs.4,000","Rs.5,500","Cash On Delivery"));
//        wishlistModelList.add(new MyWishlistModel(R.drawable.realme,"Real Me",0,"3",145,"Rs.4,000","Rs.5,500","Cash On Delivery"));
//        wishlistModelList.add(new MyWishlistModel(R.drawable.realme,"Real Me",5,"3",145,"Rs.4,000","Rs.5,500","Cash On Delivery"));
//        wishlistModelList.add(new MyWishlistModel(R.drawable.realme,"Real Me",3,"3",145,"Rs.4,000","Rs.5,500","Cash On Delivery"));

        MyWishlistAdapter cartAdapter=new MyWishlistAdapter(wishlistModelList);
        deliveryRecyclerview.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}