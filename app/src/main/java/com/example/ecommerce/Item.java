package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerce.Activities.MoreItem;
import com.example.ecommerce.Activities.Reviewitems;
import com.example.ecommerce.Adapter.MyAdapter;
import com.example.ecommerce.Fragments.MyCartFragment;
import com.skydoves.elasticviews.ElasticButton;

public class Item extends AppCompatActivity {
    RecyclerView item_horizontal,item_grid;
    TextView moreitem,review;
    ElasticButton cart,item_back;
    String[] str=new String[]{"","","","","",""};
    int[] str1=new int[]{R.drawable.gulmohar1,R.drawable.gulmohar2,R.drawable.gulmohar3,R.drawable.gulmohar4,R.drawable.gulmohar5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        item_grid=findViewById(R.id.item_grid);
        moreitem=findViewById(R.id.more_item);
        review=findViewById(R.id.review);
        cart=findViewById(R.id.cart);
        item_back=findViewById(R.id.item_back);
        item_horizontal=findViewById(R.id.item_horizontal);

        cart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(), MyCartFragment.class);
                        startActivity(intent);
                    }
                }
        );
        item_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        moreitem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(), MoreItem.class);
                        startActivity(intent);
                    }
                }
        );
        review.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(), Reviewitems.class);
                        startActivity(intent);
                    }
                }
        );


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Item.this, LinearLayoutManager.HORIZONTAL, false);
        item_horizontal.setLayoutManager(layoutManager);
        item_horizontal.setHasFixedSize(true);
        MyAdapter myAdapter=new MyAdapter(str,getApplicationContext(),str1,3);
        item_horizontal.setAdapter(myAdapter);

        RecyclerView.LayoutManager layoutManager1=new GridLayoutManager(this,2);
        item_grid.setLayoutManager(layoutManager1);
        item_grid.setHasFixedSize(true);
        MyAdapter myAdapter1=new MyAdapter(str,getApplicationContext(),str1,4);
        item_grid.setAdapter(myAdapter1);


    }
}
