package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.MyWishlistAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.MyCartModel;
import com.example.ecommerce.Model.MyWishlistModel;
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
import java.util.Map;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerview;
    private Button changeOrAddNewAddressBtn;
    private TextView totalAmount;
    private ImageView upi_selection_arrow;
    private LinearLayout paymentOptionBtn,upi_option,linear_vpa_enter,googlepay_option;
    private int count;
    private double cuttedPrice;
    private long coupons;
    FirebaseFirestore firebaseFirestore;
    private CartAdapter cartAdapter;
    private List<MyCartModel> myCartModelList;
    public static boolean ARROW_BUTTON_DOWNWARD=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        deliveryRecyclerview=findViewById(R.id.delivery_recyclerview);
        changeOrAddNewAddressBtn=findViewById(R.id.change_or_add_new_adddress);
        totalAmount=findViewById(R.id.total_amount);
        paymentOptionBtn=findViewById(R.id.payment_option);
        googlepay_option=findViewById(R.id.googlepay_option);
        upi_option=findViewById(R.id.upi_option);
        upi_selection_arrow=findViewById(R.id.upi_selection_arrow);
        linear_vpa_enter=findViewById(R.id.linear_vpa_enter);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        firebaseFirestore= FirebaseFirestore.getInstance();
//        myCartModelList = new ArrayList<>();
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
//        deliveryRecyclerview.setLayoutManager(layoutManager);
//        deliveryRecyclerview.setHasFixedSize(true);
//        getCartData();
//
//        cartAdapter=new CartAdapter(myCartModelList,getApplicationContext());
//        deliveryRecyclerview.setAdapter(cartAdapter);
//        SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
//        String value = sharedPrefManager.getTotalAmount();
//        Log.d("total amount",value);
////        totalAmount.setText(value);

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        upi_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ARROW_BUTTON_DOWNWARD){
                    ARROW_BUTTON_DOWNWARD=false;
                    upi_selection_arrow.setRotation(-90);
                    linear_vpa_enter.setVisibility(View.VISIBLE);
                }else {
                    ARROW_BUTTON_DOWNWARD=true;
                    upi_selection_arrow.setRotation(90);
                    linear_vpa_enter.setVisibility(View.GONE);
                }
            }
        });

        paymentOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        googlepay_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GooglePayMethod.class);
                startActivity(intent);
            }
        });
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
                            Toast.makeText(getApplicationContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}