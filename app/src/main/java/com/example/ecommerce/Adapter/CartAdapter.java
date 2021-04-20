package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Activities.ProfilePage;
import com.example.ecommerce.Fragments.MyCartFragment;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.CounterCartItems;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.Model.MyCartModel;
import com.example.ecommerce.Model.MyNewCartModel;
import com.example.ecommerce.Model.MyWishlistModel;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skydoves.elasticviews.ElasticImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<MyCartModel> myCartModelList;
    Context ctx;
    FirebaseFirestore firebaseFirestore;
    String p_id;
    int counter = 0;

    List<String> cartitemlist;
    int quantity;


    private int count;
    private double cuttedPrice;
    String totalSum;
    private long coupons;

    public CartAdapter(List<MyCartModel> myCartModelList, Context ctx) {
        firebaseFirestore=FirebaseFirestore.getInstance();
        this.myCartModelList = myCartModelList;
        cartitemlist = new ArrayList<String>();

        this.ctx = ctx;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, final int position) {

        p_id = myCartModelList.get(position).getC_id();
        cartitemlist.add(p_id);
        final MyCartModel myCartModel = myCartModelList.get(position);
        holder.mycart_productName.setText(myCartModelList.get(position).getC_name());
        holder.mycart_productPrice.setText(myCartModelList.get(position).getC_price());

        Picasso.get().load(myCartModelList.get(position).getC_img()).into(holder.mycart_productImage);

        if(quantity == 1)
        {
            holder.mycart_number_quantity.setText(String.valueOf(quantity));
        }

        holder.itemView.findViewById(R.id.mycart_increase_sign).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//                SharedPrefManager.getInstance(ctx).getCartValue(0);
                int count= Integer.parseInt(String.valueOf(holder.mycart_number_quantity.getText()));
                count++;
                holder.mycart_number_quantity.setText("" + count);
//                counter++;
                Toast.makeText(ctx, "Item Added", Toast.LENGTH_SHORT).show();
                String c = String.valueOf(count);
                updatedata(myCartModel.getC_id(),c);
            }

        });
        holder.itemView.findViewById(R.id.mycart_decrease_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String quantity = holder.mycart_number_quantity.getText().toString();
//                if (counter == 0) {
//                    counter = 0;
//                }
//                else{
//                    counter--;
//                }
                int count= Integer.parseInt(String.valueOf(holder.mycart_number_quantity.getText()));
                if (count == 1) {
                    holder.mycart_number_quantity.setText("1");
                } else {
                    count -= 1;
                    holder.mycart_number_quantity.setText("" + count);
                }
                holder.mycart_number_quantity.setText(String.valueOf(count));
                Toast.makeText(ctx, "Item Deleted", Toast.LENGTH_SHORT).show();
                String c = String.valueOf(count);

                updatedata(myCartModel.getC_id(),c);
            }
        });
        holder.mycart_removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartitemlist!=null){

                    FirebaseFirestore.getInstance().collection("whishlist").document(Constaints.current_user)
                            .collection("my_cart").document(p_id).delete();
                    Toast.makeText(ctx,"Data delete", Toast.LENGTH_SHORT).show();

                    myCartModelList.remove(position);
                    getCartData();
                    notifyDataSetChanged();

//                    deleteWhishlist(myCartModel.getC_id());
//                    Intent intent=new Intent(ctx,MyCartFragment.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    ctx.startActivity(intent);
                }
            }
        });

//
//
//        holder.mycart_decrease_sign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String quantity = holder.mycart_number_quantity.getText().toString();
//                if (counter == 0) {
//                    counter = 0;
//                }
//                else{
//                    counter--;
//                }
////                mycart_number_quantity.setText(String.valueOf(counter));
//                String c = String.valueOf(counter);
//
//                updatedata(c, myCartModelList.get(position).getC_id());
//            }
//        });


    }
    @Override
    public int getItemCount() {
        return myCartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mycart_decrease_sign, mycart_number_quantity, mycart_increase_sign;
        TextView mycart_productName, mycart_productPrice;
        ImageView mycart_productImage;
        ElasticImageView mycart_removeItem;

        public ViewHolder(@NonNull View itemView) {
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

    void updatedata(String id, String counter) {
        DocumentReference _ref = FirebaseFirestore.getInstance().collection("whishlist").document(Constaints.current_user)
                .collection("my_cart").document(id);

        Map<String, Object> edited = new HashMap<>();
        edited.put("p_count", counter);

            _ref.update(edited).isSuccessful();
            getCartData();
    }


    public void getCartData() {
        firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("my_cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        double sum =0.0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                try{
                                    String s = documentSnapshot.getString("product_price");
                                    double pr = Double.parseDouble(s);
                                    String c = documentSnapshot.getString("p_count");
                                    count = Integer.parseInt(c);
                                    if(count >0) {
                                        sum+= pr*count;
                                    }
                                    else{
                                        sum += 0;
                                    }
                                }
                                catch (NumberFormatException e)
                                {
                                    e.printStackTrace();
                                }
                                totalSum=String.valueOf(sum);
                                SharedPrefManager.getInstance(ctx).setTotalAmount(totalSum);
//                                Intent intent=new Intent(ctx,MyCartFragment.class);
//                                intent.putExtra("total_amount",totalSum);

                                // totalAmount.setText((int) sum);
                            }
                            DocumentReference _reference = firebaseFirestore.collection("whishlist")
                                    .document(Constaints.current_user).collection("my_cart").document(Constaints.product_id);
                            Map<String,Object> edited=new HashMap<>();
                            edited.put("total_amount",totalSum);
                            _reference.update(edited).isSuccessful();

                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(ctx,error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void deleteWhishlist(String id) {
        Log.d("Del", "ingreso a deleteAccount");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        DocumentReference _ref = firebaseFirestore.collection("wishlist").document(currentUser.getUid()).collection("my_cart").document(id);
        _ref.delete();

                    Toast.makeText(ctx, "Delete Successfull Items", Toast.LENGTH_SHORT).show();

//                firebaseFirestore.collection("wishlist").document(currentUser.getUid()).collection("my_cart").document(id).delete().
//                addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                    Toast.makeText(ctx, "Delete Successfull Items", Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ctx, "not delete Items", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

//        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d("Delete", "OK! Works fine!");
//                    DocumentReference _ref = FirebaseFirestore.getInstance().collection("whishlist").document(currentUser.getUid());
//                    _ref.delete().isSuccessful();
//                    SharedPrefManager sharedPrefManager = new SharedPrefManager(ctx);
//                    sharedPrefManager.deleteItems();
//                    Toast.makeText(ctx, "Delete Successfull Items", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e("Del", "Failed to Delete Item", e);
//            }
//        });
    }
//    public void DeleteCart(){
//        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
//        final FirebaseUser currentUser=firebaseAuth.getCurrentUser();
//        firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("my_cart").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        double sum =0.0;
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//
//                                String cutted_price=documentSnapshot.get("cutted_price").toString();
//                                String totalSum=documentSnapshot.get("total_amount").toString();
//                                String c=documentSnapshot.get("free_coupons").toString();
//                                String coupon_applied=documentSnapshot.get("coupons_applied").toString();
//                                String count=documentSnapshot.get("p_count").toString();
//
//                                double cutPrice=Double.parseDouble(cutted_price);
//                                double totalAmount=Double.parseDouble(totalSum);
//                                long coupons=Long.parseLong(c);
//                                int couponApplied=Integer.parseInt(coupon_applied);
//                                int counter=Integer.parseInt(count);
//                                ModelProducts modelProducts = new ModelProducts(documentSnapshot.getString("product_id"),
//                                        counter,
//                                        documentSnapshot.getString("product_title"),
//                                        documentSnapshot.getString("product_price"),
//                                        documentSnapshot.getString("product_image"),
//                                        cutPrice,
//                                        couponApplied,
//                                        coupons,
//                                        totalAmount);
//                            }
//                        }
//                    }
//                });
//    }


}



