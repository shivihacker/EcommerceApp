package com.example.ecommerce.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Activities.CategoryActivity;
import com.example.ecommerce.Activities.CategoryItemListActivity;
import com.example.ecommerce.Model.CategoryModel;
import com.example.ecommerce.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    int i;

    public CategoryAdapter(List<CategoryModel> categoryModelList,int i) {
        this.categoryModelList = categoryModelList;
        this.i=i;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=null;
        if(i==0) {
            view = inflater.inflate(R.layout.category_item, parent, false);

        }else if(i==1) {
            view = inflater.inflate(R.layout.activity_category_layout, parent, false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        if (i==0) {
            String icon = categoryModelList.get(position).getCategoryIconLink();
            String name = categoryModelList.get(position).getCategoryName();
            holder.setCategory(name, position);
            holder.setCategoryIcon(icon);
        }else if (i==1){
            String icon = categoryModelList.get(position).getCategoryIconLink();
            String name = categoryModelList.get(position).getCategoryName();
            holder.setCategory(name, position);
            holder.setCategoryIcon(icon);
        }

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryIcon;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (i==0) {
                categoryIcon = itemView.findViewById(R.id.category_icon);
                categoryName = itemView.findViewById(R.id.category_name);
            }else if (i==1){
                categoryIcon = itemView.findViewById(R.id.activity_category_product_icon);
                categoryName = itemView.findViewById(R.id.activity_category_item_name);
            }
        }
        private void setCategoryIcon(String iconUrl){
            // set category icon
            if (!iconUrl.equals("null")) {

                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.home)).into(categoryIcon);
            }
        }
        private void setCategory(final String name, final int position){
            if (i==0) {
                categoryName.setText(name);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            if (position == 1) {
//                            get mobile category access
                                Intent categoryIntent = new Intent(itemView.getContext(), CategoryItemListActivity.class);
                                categoryIntent.putExtra("CategoryName", name);
                                categoryIntent.putExtra("mobile_position",position);
                                Log.d("Position", "" + position);
                                itemView.getContext().startActivity(categoryIntent);

                            }else {
                            Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryIntent.putExtra("CategoryName", name);

                            categoryIntent.putExtra("position", position);
                            itemView.getContext().startActivity(categoryIntent);
                            }
                        }

                    }
                });
            }else if (i==1){
                categoryName.setText(name);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent categoryIntent = new Intent(itemView.getContext(), CategoryItemListActivity.class);
                        categoryIntent.putExtra("CategoryName", name);

                        categoryIntent.putExtra("position", position);
                        itemView.getContext().startActivity(categoryIntent);
                    }
                });

            }
        }
    }
}
