package com.example.ecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

public class MoreItemAdapter extends RecyclerView.Adapter<MoreItemAdapter.ViewHolder> {

    String[] str;
    Context ctx;

    public MoreItemAdapter(String[] str,Context ctx){
        this.str=str;
        this.ctx=ctx;
    }
    @NonNull
    @Override
    public MoreItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=null;
        view = inflater.inflate(R.layout.newmoreitem_vertical_adapt, parent, false);
        return new MoreItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreItemAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return str.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
