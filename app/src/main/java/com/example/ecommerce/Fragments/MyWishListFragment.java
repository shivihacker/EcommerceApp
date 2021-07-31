package com.example.ecommerce.Fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerce.Adapter.MyWishlistAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.Model.MyWishlistModel;
import com.example.ecommerce.Model.SliderModel;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyWishListFragment extends Fragment {
    private RecyclerView myWishlistRecyclerview;
    List<MyWishlistModel> wishlistModelList;
    List<String> wishlist;
    MyWishlistAdapter myWishlistAdapter;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        myWishlistRecyclerview=view.findViewById(R.id.my_wishlist_recyclerview);
        firebaseFirestore=FirebaseFirestore.getInstance();
        getWishlistData();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myWishlistRecyclerview.setLayoutManager(linearLayoutManager);
        wishlist=new ArrayList<>();
        wishlistModelList=new ArrayList<>();
        myWishlistAdapter=new MyWishlistAdapter(getContext(),wishlistModelList);
        myWishlistRecyclerview.setAdapter(myWishlistAdapter);
//        myWishlistAdapter.notifyDataSetChanged();
        return view;
    }
    public void getWishlistData() {
        firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("My_Wishlist").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                                long rating=0;
                                //
//                                try{
                                    String l =  documentSnapshot.getString("free_coupons");
//                                    String coup = "6";
//                                        documentSnapshot.getString("coupan");
//                                    Log.d("rating long"," " + l);
//                                    Log.d("rating coupan"," " + coup);
                                     long coupans = Long.parseLong(l);
//                                     coupan = Long.parseLong(coup);
//                                    Log.d("rating long"," " + rating);
//                                    Log.d("rating coupan"," " + coupan);
//                                }
//                                catch (NumberFormatException e)
//                                {
//                                    e.printStackTrace();
//                                }
                                wishlistModelList.add(new MyWishlistModel(documentSnapshot.get("product_id").toString(),
                                        documentSnapshot.get("product_image").toString(),
                                        documentSnapshot.getString("product_title"),
                                        coupans,
                                        documentSnapshot.getString("product_rating"),
                                        documentSnapshot.getString("total_rating"),
                                        documentSnapshot.getString("product_price"),
                                        documentSnapshot.getString("cutted_price"),
                                        documentSnapshot.getString("payment_method"),
                                        documentSnapshot.getString("selected")));
                            }
                            myWishlistAdapter.notifyDataSetChanged();
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
//    public void loadWishlist(){
//        firebaseFirestore.collection("users").document(Constaints.current_user).collection("My_Wishlist")
//                .document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if ((task.isSuccessful())){
//                    for (long x=0;x<(long)task.getResult().get("list_size");x++){
//                        wishlist.add(task.getResult().get("Product_Id"+x).toString());
//                        //firebaseFirestore.collection("CATEGORIES")
//                        firebaseFirestore.collection("CATEGORIES").document("MOBILES").collection("mobile_products").document(Constaints.product_id)
//                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()){
//                                    wishlistModelList.add(new MyWishlistModel(task.getResult().get("product_image_1").toString()
//                                            ,task.getResult().get("product_title").toString()
//                                            ,(long)task.getResult().get("free_coupons")
//                                            ,task.getResult().get("rating").toString()
//                                            ,(long)task.getResult().get("total_rating")
//                                            ,task.getResult().get("product_price").toString()
//                                            ,task.getResult().get("cutted_price").toString()
//                                            ,task.getResult().get("COD").toString()));
//                                    myWishlistAdapter.notifyDataSetChanged();;
//                                }else {
//                                    String error=task.getException().getMessage();
//                                    Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }else {
//                    String error=task.getException().getMessage();
//                    Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

}