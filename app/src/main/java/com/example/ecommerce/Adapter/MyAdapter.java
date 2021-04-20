package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.newAct.NewItem;
import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    String[] str;
    Context ctx;
    int[] str1;
    int i;

    public MyAdapter(String[] str, Context ctx, int[] str1, int i) {
        this.str = str;
        this.ctx = ctx;
        this.str1 = str1;
        this.i = i;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=null;
        if (i == 1) {
            view = inflater.inflate(R.layout.gridadapter, parent, false);
        } else if (i == 0) {
            view = inflater.inflate(R.layout.verticaladapter, parent, false);
        } else if (i == 2) {
            view = inflater.inflate(R.layout.horizontaladapter, parent, false);
        } else if (i==4){
            view = inflater.inflate(R.layout.item_grid_adapter, parent, false);
        }else {
            view = inflater.inflate(R.layout.viewmore_grid_adapt, parent, false);
        }
        return new MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        if (i==0){
        }else if (i==1){

        }else if (i==2){

        }else if (i==3){
            Picasso.get().load(str1[position]).into(holder.image);

        }else if(i==4){

        }else {

        }
        holder.line.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ctx, NewItem.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return  str1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        LinearLayout line;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img);
            line=itemView.findViewById(R.id.line);
        }
    }
}
