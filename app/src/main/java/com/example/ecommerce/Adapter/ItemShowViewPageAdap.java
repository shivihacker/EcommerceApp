package com.example.ecommerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemShowViewPageAdap extends PagerAdapter {


    ArrayList<String> list;
    Context ctx;

    public ItemShowViewPageAdap(Context ctx,ArrayList<String> list) {
        this.ctx=ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(ctx);
        imageView.setImageResource(Integer.parseInt(list.get(position)));
        Log.d("List images",list.toString());
        imageView.setBackgroundResource(list.indexOf(list.get(position)));

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        View view=(View) object;
//        container.removeView(view);
        container.removeView((ImageView)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

}
