package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.R;
import com.example.ecommerce.newAct.NewItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoreItemAdapter extends RecyclerView.Adapter<MoreItemAdapter.ViewHolder> {

    private ArrayList<ModelProducts> modelProductsList;
    Context ctx;
    List<String> wishlist;
    FirebaseFirestore firebaseFirestore;

    public MoreItemAdapter(Context ctx,ArrayList<ModelProducts> modelProductsList){
        this.ctx = ctx;
        this.modelProductsList = modelProductsList;
        firebaseFirestore= FirebaseFirestore.getInstance();
    }
    @NonNull
    @Override
    public MoreItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.newmoreitem_vertical_adapt, parent, false);
        return new MoreItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreItemAdapter.ViewHolder holder, final int position) {
        holder.moreProductTitle.setText(modelProductsList.get(position).getTitle());
        holder.moreProductRating.setText(modelProductsList.get(position).getRating());
        holder.moreProductPrice.setText(modelProductsList.get(position).getPrice());
        Picasso.get().load(modelProductsList.get(position).getImage()).into(holder.moreProductImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                Log.d("More",""+ modelProductsList.get(position).getCaregoryId());
                Intent intent=new Intent(itemView.getContext(), NewItem.class);
                intent.putExtra("id",modelProductsList.get(position).getCaregoryId());
              //  intent.putExtra("recyclerview_position",i);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moreProductImg;
        ElasticButton moreWhishlistBtn;
        TextView moreProductTitle,moreProductPrice,moreProductRating;
        private boolean ALREADY_ADDED_TO_WISHLIST=false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moreProductImg = itemView.findViewById(R.id.more_img);
            moreProductTitle = itemView.findViewById(R.id.more_title);
            moreProductPrice = itemView.findViewById(R.id.more_price);
            moreWhishlistBtn = itemView.findViewById(R.id.more_like_btn);
            moreProductRating = itemView.findViewById(R.id.more_rating);
            moreWhishlistBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constaints.current_user==null){

                    }else {
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            ALREADY_ADDED_TO_WISHLIST = false;
                            moreWhishlistBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            ALREADY_ADDED_TO_WISHLIST=true;
                            moreWhishlistBtn.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            wishlist.add(Constaints.product_id);
                            Toast.makeText(ctx, "Added to wishlist Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
