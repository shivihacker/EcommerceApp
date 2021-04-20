package com.example.ecommerce.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Model.MyWishlistModel;
import com.example.ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyWishlistAdapter extends RecyclerView.Adapter<MyWishlistAdapter.ViewHolder> {

    private List<MyWishlistModel> myWishlistModelList;

    public MyWishlistAdapter(List<MyWishlistModel> myWishlistModelList) {
        this.myWishlistModelList = myWishlistModelList;
    }

    @NonNull
    @Override
    public MyWishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishlistAdapter.ViewHolder holder, int position) {
        String title=myWishlistModelList.get(position).getProductTitle();
        long freeCoupons=myWishlistModelList.get(position).getFreeCoupons();
        String cuttedPrice=myWishlistModelList.get(position).getCuttedPrice();
        String productPrice=myWishlistModelList.get(position).getProductPrice();
        String rating=myWishlistModelList.get(position).getRating();
        String totalRatings=myWishlistModelList.get(position).getTotalRating();
        String paymentMethods=myWishlistModelList.get(position).getPaymentMethod();
        Picasso.get().load(myWishlistModelList.get(position).getProductImage()).into(holder.produtImage);
        holder.setData(title,freeCoupons,rating,totalRatings,productPrice,cuttedPrice,paymentMethods);
    }

    @Override
    public int getItemCount() {
        return myWishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView produtImage;
        private TextView produtTitle,freeCoupons,cuttedPrice,productPrice,rating,totalRating,paymentMethod;
        private ImageView couponIcon;
        private View priceCutDivider;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            produtImage=itemView.findViewById(R.id.wishlist_produt_image);
            produtTitle=itemView.findViewById(R.id.wishlist_produt_name);
            freeCoupons=itemView.findViewById(R.id.wishlist_coupons_text);
            couponIcon=itemView.findViewById(R.id.wishlist_coupons_image);
            cuttedPrice=itemView.findViewById(R.id.mycart_cut_price);
            productPrice=itemView.findViewById(R.id.mycart_product_price);
            rating=itemView.findViewById(R.id.wishlist_rating);
            totalRating=itemView.findViewById(R.id.wishlist_total_rating);
            priceCutDivider=itemView.findViewById(R.id.mycart_divider);
            deleteBtn=itemView.findViewById(R.id.wishlist_delete_item);
            paymentMethod=itemView.findViewById(R.id.wishlist_cash_on_delivery);
        }
        private void setData(String title,long freeCouponsNo,String averageRate,String totalRatingNo,
                             String price,String cuttedPriceValue,String payMethod){
            produtTitle.setText(title);
            if (freeCouponsNo!=0){
                couponIcon.setVisibility(View.VISIBLE);
                if (freeCouponsNo==1) {
                    freeCoupons.setText("free " + freeCouponsNo + " coupon");
                }else {
                    freeCoupons.setText("free " + freeCouponsNo + " coupon");
                }
            }else {
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            rating.setText(averageRate);
            totalRating.setText(totalRatingNo+" (ratings)");
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPriceValue);
            paymentMethod.setText(payMethod);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
