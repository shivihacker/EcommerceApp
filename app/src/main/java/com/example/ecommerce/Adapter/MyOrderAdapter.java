package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Model.MyOrderItemModel;
import com.example.ecommerce.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<MyOrderItemModel> myOrderItemModelList;
    Context ctx;
    FirebaseFirestore firebaseFirestore;
    int i;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList, Context ctx,int i) {
        this.myOrderItemModelList = myOrderItemModelList;
        firebaseFirestore= FirebaseFirestore.getInstance();
        this.ctx = ctx;
        this.i=i;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if (i==0){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_item,parent,false);
        }else if (i==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_item,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        if (i==0){
            String title = myOrderItemModelList.get(position).getProductTitle();
            // String deliveryDate = myOrderItemModelList.get(position).getDeliveryStatus();
            Picasso.get().load(myOrderItemModelList.get(position).getProdutImage()).into(holder.orderItemImage);

            holder.setData(title);
        }else if (i==1){
            String title = myOrderItemModelList.get(position).getProductTitle();
            // String deliveryDate = myOrderItemModelList.get(position).getDeliveryStatus();
            Picasso.get().load(myOrderItemModelList.get(position).getProdutImage()).into(holder.orderItemImage);

            holder.setData(title);
        }

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView orderItemImage;
        private TextView orderItemName;
        private TextView orderItemDeliveryStatus;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            if (i==0){
                orderItemImage=itemView.findViewById(R.id.order_item_image);
                orderItemName=itemView.findViewById(R.id.order_item_name);
                orderItemDeliveryStatus=itemView.findViewById(R.id.order_item_status);
            }else if (i==1){
                orderItemImage=itemView.findViewById(R.id.order_item_image);
                orderItemName=itemView.findViewById(R.id.order_item_name);
                orderItemDeliveryStatus=itemView.findViewById(R.id.order_item_status);
            }
        }
        private void setData(String title){
            orderItemName.setText(title);
         //   orderItemDeliveryStatus.setText(deliveryDate);
        }
    }
}
