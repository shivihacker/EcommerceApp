package com.example.ecommerce.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Activities.OrderDetailsActivity;
import com.example.ecommerce.Model.MyOrderItemModel;
import com.example.ecommerce.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
         int resource = myOrderItemModelList.get(position).getProdutImage();
         String title = myOrderItemModelList.get(position).getProductTitle();
         String deliveryDate = myOrderItemModelList.get(position).getDeliveryStatus();
         holder.setData(resource,title,deliveryDate);

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
            orderItemImage=itemView.findViewById(R.id.order_item_image);
            orderItemName=itemView.findViewById(R.id.order_item_name);
            orderItemDeliveryStatus=itemView.findViewById(R.id.order_item_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailedIntent=new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailedIntent);
                }
            });
        }
        private void setData(int resource,String title,String deliveryDate){
            orderItemImage.setImageResource(resource);
            orderItemName.setText(title);
            orderItemDeliveryStatus.setText(deliveryDate);
        }
    }
}
