package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Model.AddressModal;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressForm extends AppCompatActivity {

    private EditText city,locality,flatNo,pincode,landmark,name,mobileno,alternatemob;
    private Button saveBtn;
    private String selectedState;
    private String[] stateList = {"Up","mp"};
//    private String[] stateList = getResources().getStringArray(R.array.india_states);
    private Spinner state;
    private List<AddressModal> addressModalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);
        city=findViewById(R.id.city);
        locality=findViewById(R.id.locality);
        flatNo=findViewById(R.id.flat);
        pincode=findViewById(R.id.pincode);
        state=findViewById(R.id.state_spinner);
        landmark=findViewById(R.id.landmark);
        name=findViewById(R.id.name_address);
        mobileno=findViewById(R.id.mob_address);
        alternatemob=findViewById(R.id.alternate_mob);
        saveBtn=findViewById(R.id.save_address);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Address");
        addressModalList=new ArrayList<>();

        ArrayAdapter spinnerAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(spinnerAdapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedState=stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(city.getText())){
                    if (!TextUtils.isEmpty(locality.getText())){
                        if (!TextUtils.isEmpty(flatNo.getText())){
                            if (!TextUtils.isEmpty(pincode.getText())){
                                if (!TextUtils.isEmpty(name.getText())){
                                    if (!TextUtils.isEmpty(mobileno.getText())){

                                        String fullAddress=flatNo.getText().toString() + "  " + landmark.getText().toString()
                                                + "  " + locality.getText().toString()  + "  " + city.getText().toString() ;

                                        Map<String,Object> addAddress=new HashMap();
                                        addAddress.put("list_size",addressModalList.size()+1);
                                        if(TextUtils.isEmpty(alternatemob.getText())){
                                            addAddress.put("fullname"+addressModalList.size()+1,name.getText().toString() + " - " + mobileno.getText().toString());
                                        }else {
                                            addAddress.put("fullname"+addressModalList.size()+1,name.getText().toString() + " - " + mobileno.getText().toString() + " or " + alternatemob.getText().toString());
                                        }
                                        addAddress.put("address"+addressModalList.size()+1,fullAddress);
                                        addAddress.put("pincode"+addressModalList.size()+1,pincode.getText().toString());
                                        addAddress(name.getText().toString().trim(), mobileno.getText().toString().trim(),fullAddress, pincode.getText().toString().trim());

//                                        FirebaseFirestore.getInstance().collection("wishlist").document(Constaints.current_user)
//                                                .collection("data").document("My_Address").update(addAddress).addOnCompleteListener(
//                                                new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()){
//                                                            Intent intent=new Intent(getApplicationContext(),DeliveryActivity.class);
//                                                            startActivity(intent);
//                                                            finish();
//                                                        }else {
//                                                            String error=task.getException().getMessage();
//                                                            Toast.makeText(AddressForm.this, error, Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                }
//                                        );
                                    }else {
                                        mobileno.requestFocus();
                                    }
                                }else {
                                    name.requestFocus();
                                }
                            }else {
                                pincode.requestFocus();
                            }
                        }else {
                            flatNo.requestFocus();
                        }
                    }else {
                      locality.requestFocus();
                    }
                }else {
                    city.requestFocus();
                }
            }
        });
    }

    void addAddress(String name, String mobile, String address, String pincode){

        HashMap<String, Object> productData = new HashMap();
        productData.put("user_id", Constaints.current_user);
        productData.put("firstName", name);
        productData.put("address", address);
        productData.put("phone", mobile);
        productData.put("pincode", pincode);
        productData.put("select", true);

        DocumentReference _reference = FirebaseFirestore.getInstance().collection("user_address")
                .document(Constaints.current_user).collection("address").document("address1");
        _reference.set(productData).isSuccessful();

        Intent i = new Intent(AddressForm.this,DeliveryActivity.class);
        startActivity(i);
    }
}