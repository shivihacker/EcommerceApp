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
import com.example.ecommerce.Fragments.MyCartFragment;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Helper.WishlistSharedPref;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.Model.MyCartModel;
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

import static com.example.ecommerce.Fragments.MyCartFragment.CART_ITEM;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerview;
    private Button changeOrAddNewAddressBtn;
    private TextView totalAmount;
    private ImageView upi_selection_arrow;
    private LinearLayout paymentOptionBtn,upi_option,linear_vpa_enter,googlepay_option;
    private int count,items;
    private double cuttedPrice;
    private long coupons;
    FirebaseFirestore firebaseFirestore;
    private CartAdapter cartAdapter;
    String totalamount,sellingPrice;
    double amount,discount,specialPrice,shippingCharge;
    private List<MyCartModel> myCartModelList;
    public static final int SELECT_ADDRESS=0;
    private TextView selling_price, extra_discount, special_price,shipping_fee;

    private TextView shipping_address,shippling_phone,shippling_pincode, total_amount_price;
    String address, phone,pinArea;

    boolean checkadd=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        /////////////////////////////////////
//        SharedPrefManager.getInstance(getApplicationContext()).getAddress(Constaints.current_user);

        deliveryRecyclerview=findViewById(R.id.delivery_recyclerview);
        changeOrAddNewAddressBtn=findViewById(R.id.change_or_add_new_adddress);
        totalAmount=findViewById(R.id.total_amount);
        paymentOptionBtn=findViewById(R.id.payment_option);
        googlepay_option=findViewById(R.id.googlepay_option);
        upi_option=findViewById(R.id.upi_option);
        upi_selection_arrow=findViewById(R.id.upi_selection_arrow);
        linear_vpa_enter=findViewById(R.id.linear_vpa_enter);
        shipping_address = findViewById(R.id.shipping_address);
        shippling_phone = findViewById(R.id.shipping_phone_no);
        shippling_pincode = findViewById(R.id.shipping_pincode);

        if (checkadd) {
            checkAddressInDatabase(Constaints.current_user);
        }else {
            addData();
        }
        Log.d("Address my:",WishlistSharedPref.getInstance(getApplicationContext()).getAddress("ADDRESS"));
        Log.d("Phone my:",WishlistSharedPref.getInstance(getApplicationContext()).getPhone("PHONE"));
        Log.d("Pincode my:",WishlistSharedPref.getInstance(getApplicationContext()).getPincode("PINCODE"));


//      price details amount portion
        selling_price = findViewById(R.id.total_amount_selling_price);
        extra_discount = findViewById(R.id.total_extra_discount_price);
        special_price = findViewById(R.id.total_special_price);
        shipping_fee = findViewById(R.id.total_shipping_fee_price);
        total_amount_price = findViewById(R.id.total_amount_price);

        String value="0";
        extra_discount.setText(value);
        discount=Double.parseDouble(extra_discount.getText().toString());
        special_price.setText(value);
        specialPrice=Double.parseDouble(special_price.getText().toString());
        shipping_fee.setText(value);
        shippingCharge=Double.parseDouble(shipping_fee.getText().toString());

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        if (items==CART_ITEM){}
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        firebaseFirestore= FirebaseFirestore.getInstance();
        myCartModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        deliveryRecyclerview.setLayoutManager(layoutManager);
        deliveryRecyclerview.setHasFixedSize(true);
        getCartDataa();

        cartAdapter=new CartAdapter(myCartModelList,getApplicationContext(),totalAmount,1);
        deliveryRecyclerview.setAdapter(cartAdapter);
   //     SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
  //      String value = sharedPrefManager.getTotalAmount();
//        Log.d("total amount",value);
////        totalAmount.setText(value);

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MyAddressAct.class);
                intent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(intent);
            }
        });


        paymentOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GooglePayMethod.class);
                startActivity(intent);
            }
        });
        googlepay_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        shipping_address.setText(WishlistSharedPref.getInstance(getApplicationContext()).getAddress("ADDRESS"));
        shippling_phone.setText(WishlistSharedPref.getInstance(getApplicationContext()).getPhone("PHONE"));
        shippling_pincode.setText("-"+WishlistSharedPref.getInstance(getApplicationContext()).getPincode("PINCODE"));

    }

    public void getCartDataa() {
        firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("my_cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        double sellprice=0.0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                 totalamount=documentSnapshot.get("total_amount").toString();
                                double sum=Double.parseDouble(totalamount);

                                String c = documentSnapshot.getString("p_count");
                                count = Integer.parseInt(c);

                                String s = documentSnapshot.getString("product_price");
                                double pr = Double.parseDouble(s);

                                if(count >0) {
                                    sellprice+= pr*count;
                                }
                                else{
                                    sellprice += pr;
                                }
                                sellingPrice=String.valueOf(sellprice);
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
                              //  totalAmount.setText(totalamount);
                            }
                            cartAdapter.notifyDataSetChanged();
                            amount=sellprice-discount+specialPrice+shippingCharge;
                            String aMount=String.valueOf(amount);
                            selling_price.setText(sellingPrice);
                            total_amount_price.setText(aMount);

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

    public void checkAddressInDatabase(String id) {
        checkadd=true;
        FirebaseFirestore.getInstance().collection("user_address").document(Constaints.current_user).collection("address").document("address1").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            address = documentSnapshot.getString("address");
                            phone = documentSnapshot.getString("phone");
                            pinArea = documentSnapshot.getString("phone");
                            WishlistSharedPref.getInstance(getApplicationContext()).setAddress(address,pinArea,phone);

                            Log.d("address",documentSnapshot.getString("address"));
                            Log.d("phone",documentSnapshot.getString("phone"));
                            Log.d("hello","hello hello");

                            addData();
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Add Address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addData() {
        shipping_address.setText(WishlistSharedPref.getInstance(getApplicationContext()).getAddress("ADDRESS"));
        shippling_phone.setText(WishlistSharedPref.getInstance(getApplicationContext()).getPhone("PHONE"));
        shippling_pincode.setText("-"+WishlistSharedPref.getInstance(getApplicationContext()).getPincode("PINCODE"));
    }

}