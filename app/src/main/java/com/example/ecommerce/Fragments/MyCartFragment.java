package com.example.ecommerce.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Activities.DeliveryActivity;
import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.MyWishlistAdapter;
import com.example.ecommerce.Adapter.ProductAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.Model.MyCartModel;
import com.example.ecommerce.Model.MyNewCartModel;
import com.example.ecommerce.Model.MyWishlistModel;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyCartFragment extends Fragment {

    RecyclerView vertical_cart;
    private TextView totalAmount;
    String[] str=new String[]{"","","","","",""};
    private Button mycartContinueButton;
    private int count;
    private double cuttedPrice;
    private long coupons;
    List<MyCartModel> myCartModelList;
    CartAdapter cartAdapter;
    FirebaseFirestore firebaseFirestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_cart, container, false);
        vertical_cart=view.findViewById(R.id.vertical_cart);
        totalAmount=view.findViewById(R.id.total_amount);
        mycartContinueButton=view.findViewById(R.id.mycart_continue_btn);
        firebaseFirestore=FirebaseFirestore.getInstance();
        myCartModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        vertical_cart.setLayoutManager(layoutManager);
        vertical_cart.setHasFixedSize(true);
        getCartData();

        cartAdapter=new CartAdapter(myCartModelList,getActivity());
        vertical_cart.setAdapter(cartAdapter);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        String value = sharedPrefManager.getTotalAmount();

        Log.d("total amount",value);
        totalAmount.setText(value);

        //totalAmount.setText(SharedPrefManager.getInstance(getActivity()).getTotalAmount();
//        getProducts();
//        CartAdapter myAdapter=new CartAdapter(myNewCartModelList);
//        vertical_cart.setAdapter(myAdapter);

        mycartContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DeliveryActivity.class);
                startActivity(intent);
            }
        });


        return view;
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
                                    String coupan=documentSnapshot.getString("free_coupons");
                                    String cutPrice=documentSnapshot.getString("cutted_price");
                                    cuttedPrice=Double.parseDouble(cutPrice);
                                     count = Integer.parseInt(c);
                                     coupons=Long.parseLong(coupan);
                                    if(count >0) {
                                        sum+= pr*count;
                                    }
                                    else{
                                        sum += pr;
                                    }
                                }
                                catch (NumberFormatException e)
                                {
                                    e.printStackTrace();
                                }
                                String totalSum=String.valueOf(sum);
                                DocumentReference _reference = firebaseFirestore.collection("whishlist")
                                    .document(Constaints.current_user).collection("my_cart").document(Constaints.product_id);
                            Map<String,Object> edited=new HashMap<>();
                            edited.put("total_amount",totalSum);
                            _reference.update(edited).isSuccessful();

                                myCartModelList.add(new MyCartModel(
                                        documentSnapshot.get("product_id").toString(),
                                        documentSnapshot.get("product_image").toString(),
                                        documentSnapshot.getString("product_title"),
                                        documentSnapshot.getString("product_price"),
                                        cuttedPrice,
                                        sum,
                                        coupons,
                                       0,
                                        count));

                               // totalAmount.setText((int) sum);

                                totalAmount.setText(totalSum);
                            }

                            cartAdapter.notifyDataSetChanged();
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    public void getProducts()
//    {
//        firebaseFirestore.collection("whishlist")
//                .document(Constaints.current_user).collection("my_cart").document(Constaints.product_id).get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful()) {
//                            DocumentSnapshot documentSnapshot = task.getResult();
//
//                                MyNewCartModel myNewCartModel = new MyNewCartModel(
//                                        documentSnapshot.getString("product_id"),
//                                        documentSnapshot.getString("product_name"),
//                                        documentSnapshot.getString("product_price"),
//                                        documentSnapshot.getString("product_image")
//                                );
//
//                                myNewCartModelList.add(myNewCartModel);
////                            productAdapter=new ProductAdapter(modelProductsList,0);
////                            grid.setAdapter(productAdapter);
//                            Log.d("MyCart",myNewCartModelList.get(2).getName());
//
//                        }
//                    }
//                });
//    }

}