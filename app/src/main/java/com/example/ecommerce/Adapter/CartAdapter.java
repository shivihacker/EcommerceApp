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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Activities.LoginPage;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skydoves.elasticviews.ElasticImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<MyCartModel> myCartModelList;
    Context ctx;
    FirebaseFirestore firebaseFirestore;
    String p_id;
    List<String> cartitemlist;
    private int quantity;
    private int count;
    private double cuttedPrice;
    String totalSum;
    TextView totalAmount;
    private long coupons;
    int i;

    public CartAdapter(List<MyCartModel> myCartModelList, Context ctx,TextView totalAmount,int i) {
        firebaseFirestore=FirebaseFirestore.getInstance();
        this.myCartModelList = myCartModelList;
        cartitemlist = new ArrayList<String>();
        this.ctx = ctx;
        this.totalAmount=totalAmount;
        this.i=i;
    }

    public CartAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if (i==0){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item_layout, parent, false);
        }else if (i==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item_layout, parent, false);
        }
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, final int position) {
        if (i==0){
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
                    updateAllDataTotalAmount();
                    //  holder.mycart_number_quantity.setText("" + count);
                }

            });
            holder.itemView.findViewById(R.id.mycart_decrease_sign).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count= Integer.parseInt(String.valueOf(holder.mycart_number_quantity.getText()));
                    if (count == 1) {
                        holder.mycart_number_quantity.setText("1");
                    } else {
                        count -= 1;
                        holder.mycart_number_quantity.setText("" + count);
                    }
//                holder.mycart_number_quantity.setText(String.valueOf(count));
                    Toast.makeText(ctx, "Item Deleted", Toast.LENGTH_SHORT).show();
                    String c = String.valueOf(count);
                    updatedata(myCartModel.getC_id(),c);
                    updateAllDataTotalAmount();
                    //  holder.mycart_number_quantity.setText(String.valueOf(count));
                }
            });
            holder.mycart_removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartitemlist!=null){

                        FirebaseFirestore.getInstance().collection("whishlist").document(Constaints.current_user)
                                .collection("my_cart").document(p_id).delete();
                        Toast.makeText(ctx,"Data deleted", Toast.LENGTH_SHORT).show();

                        myCartModelList.remove(position);
                        getAmountData();
                        updateAllDataTotalAmount();
                        notifyDataSetChanged();

                    }
                }
            });
        }else if (i==1){
            p_id = myCartModelList.get(position).getC_id();
            cartitemlist.add(p_id);
            final MyCartModel myCartModel = myCartModelList.get(position);
            holder.deliveryCart_productName.setText(myCartModelList.get(position).getC_name());
            holder.deliveryCart_productPrice.setText(myCartModelList.get(position).getC_price());
            //////////////////////////////////////////
//            firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("my_cart").document(p_id)
//                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot documentSnapshot = task.getResult();
//                        number = documentSnapshot.getString("p_count");
//                    }
//                }
//            });
            /////////////////////////////
            //holder.deliverCart_number_quantity.setText(""+count);
            Picasso.get().load(myCartModelList.get(position).getC_img()).into(holder.deliveryCart_productImage);

            if(quantity == 1)
            {
                holder.deliverCart_number_quantity.setText(String.valueOf(quantity));
            }

            holder.itemView.findViewById(R.id.mycart_increase_sign).setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
//                SharedPrefManager.getInstance(ctx).getCartValue(0);
                    int count= Integer.parseInt(String.valueOf(holder.deliverCart_number_quantity.getText()));
                    count++;
                    holder.deliverCart_number_quantity.setText("" + count);
//                counter++;
                    Toast.makeText(ctx, "Item Added", Toast.LENGTH_SHORT).show();
                    String c = String.valueOf(count);
                    updatedata(myCartModel.getC_id(),c);
                    updateAllDataTotalAmount();
                    //  holder.mycart_number_quantity.setText("" + count);
                }

            });
            holder.itemView.findViewById(R.id.mycart_decrease_sign).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count= Integer.parseInt(String.valueOf(holder.deliverCart_number_quantity.getText()));
                    if (count == 1) {
                        holder.deliverCart_number_quantity.setText("1");
                    } else {
                        count -= 1;
                        holder.deliverCart_number_quantity.setText("" + count);
                    }
//                holder.mycart_number_quantity.setText(String.valueOf(count));
                    Toast.makeText(ctx, "Item Deleted", Toast.LENGTH_SHORT).show();
                    String c = String.valueOf(count);
                    updatedata(myCartModel.getC_id(),c);
                    updateAllDataTotalAmount();
                    //  holder.mycart_number_quantity.setText(String.valueOf(count));
                }
            });
            holder.deliveryCart_removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartitemlist!=null){

                        FirebaseFirestore.getInstance().collection("whishlist").document(Constaints.current_user)
                                .collection("my_cart").document(p_id).delete();
                        Toast.makeText(ctx,"Data deleted", Toast.LENGTH_SHORT).show();

                        myCartModelList.remove(position);
                        getAmountData();
                        updateAllDataTotalAmount();
                        notifyDataSetChanged();

                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return myCartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mycart_decrease_sign, mycart_number_quantity, mycart_increase_sign;
        TextView deliveryCart_decrease_sign, deliverCart_number_quantity, deliveryCart_increase_sign;
        TextView mycart_productName, mycart_productPrice;
        TextView deliveryCart_productName, deliveryCart_productPrice;
        ImageView mycart_productImage,deliveryCart_productImage;
        ElasticImageView mycart_removeItem,deliveryCart_removeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (i==0){
                mycart_decrease_sign = itemView.findViewById(R.id.mycart_decrease_sign);
                mycart_increase_sign = itemView.findViewById(R.id.mycart_increase_sign);
                mycart_number_quantity = itemView.findViewById(R.id.mycart_number_quantity);
                mycart_productImage = itemView.findViewById(R.id.mycart_product_img);
                mycart_removeItem = itemView.findViewById(R.id.mycart_remove_items);
                mycart_productName = itemView.findViewById(R.id.mycart_product_name);
                mycart_productPrice = itemView.findViewById(R.id.mycart_product_price);
            }
           else if (i==1){
                deliveryCart_decrease_sign = itemView.findViewById(R.id.mycart_decrease_sign);
                deliveryCart_increase_sign = itemView.findViewById(R.id.mycart_increase_sign);
                deliverCart_number_quantity = itemView.findViewById(R.id.mycart_number_quantity);
                deliveryCart_productImage = itemView.findViewById(R.id.mycart_product_img);
                deliveryCart_removeItem = itemView.findViewById(R.id.mycart_remove_items);
                deliveryCart_productName = itemView.findViewById(R.id.mycart_product_name);
                deliveryCart_productPrice = itemView.findViewById(R.id.mycart_product_price);
           }

        }
    }

    void updatedata(String id, String counter) {
        DocumentReference _ref = FirebaseFirestore.getInstance().collection("whishlist").document(Constaints.current_user)
                .collection("my_cart").document(id);

        Map<String, Object> edited = new HashMap<>();
        edited.put("p_count", counter);

            _ref.update(edited).isSuccessful();

            getAmountData();
    }


    public void getAmountData() {
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
    public void updateAllDataTotalAmount() {
        firebaseFirestore.collection("whishlist")
                .document(Constaints.current_user).collection("my_cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("mydata", document.getId() + " => " + document.getData());

                                document.getReference().update("total_amount", totalSum);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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

    }
}



