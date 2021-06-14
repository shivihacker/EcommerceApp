package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Activities.OrderDetailsActivity;
import com.example.ecommerce.Fragments.DetailsFragment;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.R;
import com.example.ecommerce.newAct.NewItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<ModelProducts> modelProductsList;
    //String id;
    int i;
    Context ctx;
    List<String> wishlist;
    FirebaseFirestore firebaseFirestore;
    public static final int CATEGORY_LIST=1;
    public static final int HOME_LIST=0;

    public ProductAdapter(Context ctx,ArrayList<ModelProducts> modelProductsList, int i) {
        this.ctx = ctx;
        this.modelProductsList = modelProductsList;
        this.i=i;
        firebaseFirestore= FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gridadapter,parent,false);
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=null;
        if(i==0) {
            view = inflater.inflate(R.layout.gridadapter, parent, false);

        }else if(i==1) {
            view = inflater.inflate(R.layout.horizontaladapter, parent, false);

        }else if(i==2) {
            view = inflater.inflate(R.layout.verticaladapter, parent, false);
        }else if (i==3) {
            view = inflater.inflate(R.layout.verticaladapter, parent, false);
        }
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, final int position) {
        if (i==0){
            holder.AdapterProductTitle.setText(modelProductsList.get(position).getTitle());
            holder.AdapterRating.setText(modelProductsList.get(position).getRating());
            holder.AdapterProductPrice.setText(modelProductsList.get(position).getPrice());
            Picasso.get().load(modelProductsList.get(position).getImage()).into(holder.AdapterproductImg);
            //////////
//            Constaints.product_name = modelProductsList.get(position).getName();
//            Constaints.product_image = modelProductsList.get(position).getImage();
//            Constaints.product_price = modelProductsList.get(position).getPrice();
            /////////////
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    Log.d("Hello Kabaddi",""+ modelProductsList.get(position).getCaregoryId());
                    Intent intent=new Intent(itemView.getContext(), NewItem.class);
                    intent.putExtra("id",modelProductsList.get(position).getCaregoryId());
                    intent.putExtra("recyclerview_position",i);
                    intent.putExtra("SELECTION",HOME_LIST);
                    itemView.getContext().startActivity(intent);
                }
            });

        }else  if(i==1){
            holder.AdapterProductTitle.setText(modelProductsList.get(position).getTitle());
            holder.AdapterRating.setText(modelProductsList.get(position).getRating());
            holder.AdapterProductPrice.setText(modelProductsList.get(position).getPrice());
            Picasso.get().load(modelProductsList.get(position).getImage()).into(holder.AdapterproductImg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    Log.d("Hello Kabaddi",""+ modelProductsList.get(position).getCaregoryId());
                    Intent intent=new Intent(itemView.getContext(), NewItem.class);
                    intent.putExtra("id",modelProductsList.get(position).getCaregoryId());
                    intent.putExtra("recyclerview_position",i);
                    intent.putExtra("SELECTION",HOME_LIST);
                    itemView.getContext().startActivity(intent);
                }
            });
        }else if(i==2){
            holder.AdapterProductTitle.setText(modelProductsList.get(position).getTitle());
            holder.AdapterRating.setText(modelProductsList.get(position).getRating());
            holder.AdapterProductPrice.setText(modelProductsList.get(position).getPrice());
            Picasso.get().load(modelProductsList.get(position).getImage()).into(holder.AdapterproductImg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    Log.d("Second Recycler",""+ modelProductsList.get(position).getCaregoryId());
                    Intent intent=new Intent(itemView.getContext(), NewItem.class);
                    intent.putExtra("id",modelProductsList.get(position).getCaregoryId());
                    intent.putExtra("recyclerview_position",i);
                    intent.putExtra("SELECTION",HOME_LIST);
                    itemView.getContext().startActivity(intent);
                }
            });

        }else if (i==3){
            holder.AdapterProductTitle.setText(modelProductsList.get(position).getTitle());
            holder.AdapterRating.setText(modelProductsList.get(position).getRating());
            holder.AdapterProductPrice.setText(modelProductsList.get(position).getPrice());
            Picasso.get().load(modelProductsList.get(position).getImage()).into(holder.AdapterproductImg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                   // String categoryId=modelProductsList.get(position).getCaregoryId();
                    Log.d("Second Recycler",""+ modelProductsList.get(position).getCaregoryId());
                   // Log.d("caegoryId",categoryId);
                    Intent intent=new Intent(itemView.getContext(), NewItem.class);
                    intent.putExtra("id",modelProductsList.get(position).getCaregoryId());
                    intent.putExtra("recyclerview_position",i);
                    intent.putExtra("SELECTION",CATEGORY_LIST);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modelProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView AdapterproductImg;
        ElasticButton gridAdapterwhishlist,verticalAdapterwhishlist,horizontalAdapterwishlist;
        TextView AdapterProductTitle, gridAdapterProductDetail,AdapterProductPrice,AdapterRating,
                gridAdapterShare;
        private boolean ALREADY_ADDED_TO_WISHLIST=false;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            if (i==0){
                AdapterproductImg = itemView.findViewById(R.id.grid_adapter_img);
                AdapterProductTitle = itemView.findViewById(R.id.grid_adapter_productname);
                AdapterProductPrice = itemView.findViewById(R.id.grid_adapter_productprice);
                gridAdapterwhishlist = itemView.findViewById(R.id.grid_adapter_wishlist);
                AdapterRating = itemView.findViewById(R.id.grid_adapter_rating);
                gridAdapterwhishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Constaints.current_user==null){

                        }else {
                            if (ALREADY_ADDED_TO_WISHLIST) {
                                ALREADY_ADDED_TO_WISHLIST = false;
                                gridAdapterwhishlist.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                            } else {
                                ALREADY_ADDED_TO_WISHLIST=true;
                                gridAdapterwhishlist.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                                wishlist.add(Constaints.product_id);
                                Toast.makeText(ctx, "Added to wishlist Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }else if(i==1){
                AdapterproductImg = itemView.findViewById(R.id.horizontal_adapter_img);
                AdapterProductTitle = itemView.findViewById(R.id.horizontal_adapter_product_name);
                AdapterRating = itemView.findViewById(R.id.horizontal_adapter_product_rating);
                AdapterProductPrice = itemView.findViewById(R.id.horizontal_adapter_product_price);
                horizontalAdapterwishlist = itemView.findViewById(R.id.horizontal_adapter_wishlist);
                horizontalAdapterwishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ALREADY_ADDED_TO_WISHLIST){
                            ALREADY_ADDED_TO_WISHLIST=false;
                            horizontalAdapterwishlist.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        }
                        else {
                            ALREADY_ADDED_TO_WISHLIST=true;
                            horizontalAdapterwishlist.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        }
                    }
                });
            }else if(i==2){
                AdapterproductImg = itemView.findViewById(R.id.vertical_adapter_img);
                AdapterProductTitle = itemView.findViewById(R.id.vertical_adapter_product_name);
                AdapterRating = itemView.findViewById(R.id.vertical_adapter_product_rating);
                AdapterProductPrice = itemView.findViewById(R.id.vertical_adapter_product_price);
                verticalAdapterwhishlist = itemView.findViewById(R.id.vertical_adapter_wishlist);
                verticalAdapterwhishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ALREADY_ADDED_TO_WISHLIST){
                            ALREADY_ADDED_TO_WISHLIST=false;
                            verticalAdapterwhishlist.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        }
                        else {
                            ALREADY_ADDED_TO_WISHLIST=true;
                            verticalAdapterwhishlist.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        }
                    }
                });

            }else if(i==3){
                AdapterproductImg = itemView.findViewById(R.id.vertical_adapter_img);
                AdapterProductTitle = itemView.findViewById(R.id.vertical_adapter_product_name);
                AdapterRating = itemView.findViewById(R.id.vertical_adapter_product_rating);
                AdapterProductPrice = itemView.findViewById(R.id.vertical_adapter_product_price);
                verticalAdapterwhishlist = itemView.findViewById(R.id.vertical_adapter_wishlist);
                verticalAdapterwhishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ALREADY_ADDED_TO_WISHLIST){
                            ALREADY_ADDED_TO_WISHLIST=false;
                            verticalAdapterwhishlist.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        }
                        else {
                            ALREADY_ADDED_TO_WISHLIST=true;
                            verticalAdapterwhishlist.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        }
                    }
                });

            }
        }
    }

}
