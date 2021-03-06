package com.example.ecommerce.NewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Model.SliderModel;
import com.example.ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class SliderHomeAdapter extends PagerAdapter {

    private ArrayList<SliderModel> sliderModelList;


    public SliderHomeAdapter(ArrayList<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.slider_homepage_layout,container,false);
        ImageView banner=view.findViewById(R.id.banner_slide_homepage);
//        Picasso.get().load(sliderModelList.get(position).getBanner()).into(banner);

        Glide.with(container.getContext()).load(sliderModelList.get(position)).apply(new RequestOptions()
                .placeholder(R.drawable.home)).into(banner);

        container.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sliderModelList.size();
    }
}
