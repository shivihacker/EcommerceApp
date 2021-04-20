package com.example.ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecommerce.Adapter.MoreItemAdapter;
import com.example.ecommerce.Adapter.MyAdapter;
import com.example.ecommerce.Fragments.MyCartFragment;
import com.example.ecommerce.R;
import com.skydoves.elasticviews.ElasticButton;

public class MoreItem extends AppCompatActivity {
    RecyclerView vertical_more;
    ElasticButton moreitem_back,cart;
    String[] str=new String[]{"","","","","","","","","",""};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_item);
        moreitem_back=findViewById(R.id.moreitem_back);
        vertical_more=findViewById(R.id.vertical_more);
        cart=findViewById(R.id.cart);

        cart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(), MyCartFragment.class);
                        startActivity(intent);
                    }
                }
        );
        moreitem_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        vertical_more.setLayoutManager(layoutManager);
        vertical_more.setHasFixedSize(true);
        MoreItemAdapter myAdapter=new MoreItemAdapter(str,getApplicationContext());
        vertical_more.setAdapter(myAdapter);
    }
}
