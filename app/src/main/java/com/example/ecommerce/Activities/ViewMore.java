package com.example.ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecommerce.Adapter.MyAdapter;
import com.example.ecommerce.R;

public class ViewMore extends AppCompatActivity {
    RecyclerView viewmore_grid;
    String[] str=new String[]{"","","","","",""};
    int[] str2=new int[]{R.drawable.gulmohar1,R.drawable.gulmohar2,R.drawable.gulmohar3,R.drawable.gulmohar4,R.drawable.gulmohar5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);

        viewmore_grid=findViewById(R.id.viewmore_grid);

        RecyclerView.LayoutManager layoutManager1=new GridLayoutManager(this,2);
        viewmore_grid.setLayoutManager(layoutManager1);
        viewmore_grid.setHasFixedSize(true);
        MyAdapter myAdapter1=new MyAdapter(str,getApplicationContext(),str2,6);
        viewmore_grid.setAdapter(myAdapter1);
    }
}
