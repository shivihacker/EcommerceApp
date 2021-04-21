package com.example.ecommerce.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerce.Model.MyCartModel;
import com.example.ecommerce.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<MyCartModel> items;
    private OnItemClickListener onItemClickListener;
    Context ctx;
    FirebaseFirestore firebaseFirestore;

    public RecyclerViewAdapter(ArrayList<MyCartModel> myCartModelList, Context ctx) {
        firebaseFirestore= FirebaseFirestore.getInstance();
        this.items = myCartModelList;

        this.ctx = ctx;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item_layout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    public void updateData(ArrayList<MyCartModel> viewModels) {
        items.clear();
        items.addAll(viewModels);
        notifyDataSetChanged();
    }
    public void addItem(int position, MyCartModel viewModel) {
        items.add(position, viewModel);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }


int count =0;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       String p_id = items.get(position).getC_id();
        final MyCartModel myCartModel = items.get(position);
        holder.mycart_productName.setText(items.get(position).getC_name());
        holder.mycart_productPrice.setText(items.get(position).getC_price());

        Picasso.get().load(items.get(position).getC_img()).into(holder.mycart_productImage);


        holder.mycart_increase_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+1;
                holder.mycart_number_quantity.setText(String.valueOf(count));


            }
        });


        holder.itemView.setTag(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (ViewModel) v.getTag());
                }
            }, 0);
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mycart_decrease_sign, mycart_number_quantity, mycart_increase_sign;
        TextView mycart_productName, mycart_productPrice;
        ImageView mycart_productImage;
        ElasticImageView mycart_removeItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mycart_decrease_sign = itemView.findViewById(R.id.mycart_decrease_sign);
            mycart_increase_sign = itemView.findViewById(R.id.mycart_increase_sign);
            mycart_number_quantity = itemView.findViewById(R.id.mycart_number_quantity);
            mycart_productImage = itemView.findViewById(R.id.mycart_product_img);
            mycart_removeItem = itemView.findViewById(R.id.mycart_remove_items);
            mycart_productName = itemView.findViewById(R.id.mycart_product_name);
            mycart_productPrice = itemView.findViewById(R.id.mycart_product_price);

        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ViewModel viewModel);

    }
}
