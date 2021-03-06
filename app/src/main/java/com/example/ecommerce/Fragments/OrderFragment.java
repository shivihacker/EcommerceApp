package com.example.ecommerce.Fragments;

import android.content.Intent;
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

import com.example.ecommerce.Adapter.MyOrderAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.WishlistSharedPref;
import com.example.ecommerce.Model.MyCartModel;
import com.example.ecommerce.Model.MyOrderItemModel;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OrderFragment extends Fragment {
    private RecyclerView myOrderRecyclerView;
//    private ArrayList<MyCartModel> myCartModelList;
    private ArrayList<MyOrderItemModel> myCartModelList;
    FirebaseFirestore firebaseFirestore;
    private MyOrderAdapter orderNewActivityAdapter;
    TextView receipt, shipping_address, phone, total_amount;
    Button go_to_home;
    String pay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order, container, false);

        myOrderRecyclerView=view.findViewById(R.id.my_order_recyclerview);
        firebaseFirestore= FirebaseFirestore.getInstance();
        getCartDataa();

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

 //       List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();
        myCartModelList =new ArrayList<>();
        orderNewActivityAdapter=new MyOrderAdapter(myCartModelList,getActivity(),1);
        myOrderRecyclerView.setAdapter(orderNewActivityAdapter);
//        MyOrderAdapter myOrderAdapter=new MyOrderAdapter(myOrderItemModelList);
//        myOrderRecyclerView.setAdapter(myOrderAdapter);
//        myOrderAdapter.notifyDataSetChanged();
        return view;
    }

    public void getCartDataa() {
        firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("my_cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        double sellprice=0.0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                                total_amount.setText(documentSnapshot.get("total_amount").toString());
                                double a = Double.parseDouble(documentSnapshot.getString("cutted_price"));

//                               int c_applied = Integer.parseInt(documentSnapshot.getString("coupons_applied"));
                                int count_item = Integer.parseInt(documentSnapshot.getString("p_count"));
                                myCartModelList.add(new MyOrderItemModel(
                                        documentSnapshot.get("product_id").toString(),
                                        documentSnapshot.get("product_image").toString(),
                                        documentSnapshot.getString("product_title")
//                                    //    documentSnapshot.getString("product_price"),
//                                        a,
//                                        Double.parseDouble( documentSnapshot.get("total_amount").toString()),
//                                        0,
//                                        0,
//                                        count_item
                                        ));
                            }
                      //      saveOrderData(myCartModelList);
                            orderNewActivityAdapter.notifyDataSetChanged();


                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    public void saveOrderData(ArrayList<MyOrderItemModel> arrayList) {
//        ArrayList<String> myList = new ArrayList<>();
//        HashMap<String, String> orderData = new HashMap();
//        orderData.put("uid", Constaints.current_user);
//        orderData.put("taxation_id", pay);
//        orderData.put("total_amount", total_amount.getText().toString().trim());
//        orderData.put("address", WishlistSharedPref.getInstance(getActivity()).getAddress("ADDRESS"));
//        orderData.put("phone", WishlistSharedPref.getInstance(getActivity()).getPhone("PHONE"));
//        for (int i =0; i<myCartModelList.size();i++)
//        {
//            myList.add(myCartModelList.get(i).getC_id());
//        }
//        orderData.put("product_list", myList.toString());
//
//        DocumentReference _reference = FirebaseFirestore.getInstance().collection("order")
//                .document(Constaints.current_user).collection("my_order").document(Constaints.product_id);
//        _reference.set(orderData).isSuccessful();
//    }
}