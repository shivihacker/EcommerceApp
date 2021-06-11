package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class OrderNewActivity extends AppCompatActivity {

    private RecyclerView cartRecycle;
    private ArrayList<MyOrderItemModel> myCartModelList;
    FirebaseFirestore firebaseFirestore;
    private MyOrderAdapter orderNewActivityAdapter;
    TextView receipt, shipping_address, phone, total_amount;

    Button go_to_home;
    String pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_new);


        cartRecycle=findViewById(R.id.my_order_recyclerview);
        receipt = findViewById(R.id.receipt);
        shipping_address =findViewById(R.id.order_shipping_address1);
        phone =findViewById(R.id.order_shipping_phone_no);
        total_amount =findViewById(R.id.order_total_amount);
        firebaseFirestore= FirebaseFirestore.getInstance();
        getCartDataa();

        Intent i = getIntent();
        pay = i.getStringExtra("pay_details");

        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecycle.setLayoutManager(layoutManager);
        myCartModelList =new ArrayList<>();
        orderNewActivityAdapter=new MyOrderAdapter(myCartModelList,getApplicationContext(),0);
        cartRecycle.setAdapter(orderNewActivityAdapter);

        receipt.setText(pay);
        shipping_address.setText(WishlistSharedPref.getInstance(getApplicationContext()).getAddress("ADDRESS"));
        phone.setText(WishlistSharedPref.getInstance(getApplicationContext()).getPhone("PHONE"));

        return;
    }

    public void getCartDataa() {
        firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("my_cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        double sellprice=0.0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                total_amount.setText(documentSnapshot.get("total_amount").toString());
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
                            saveOrderData(myCartModelList);
                            orderNewActivityAdapter.notifyDataSetChanged();


                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void saveOrderData(ArrayList<MyOrderItemModel> arrayList) {
        ArrayList<String> myList = new ArrayList<>();
        HashMap<String, String> orderData = new HashMap();
        orderData.put("uid", Constaints.current_user);
        orderData.put("taxation_id", pay);
        orderData.put("total_amount", total_amount.getText().toString().trim());
        orderData.put("address", WishlistSharedPref.getInstance(getApplicationContext()).getAddress("ADDRESS"));
        orderData.put("phone", WishlistSharedPref.getInstance(getApplicationContext()).getPhone("PHONE"));
        for (int i =0; i<myCartModelList.size();i++)
        {
            myList.add(myCartModelList.get(i).getC_id());
        }
        orderData.put("product_list", myList.toString());

        DocumentReference _reference = FirebaseFirestore.getInstance().collection("order")
                .document(Constaints.current_user).collection("my_order").document(Constaints.product_id);
        _reference.set(orderData).isSuccessful();
    }
}