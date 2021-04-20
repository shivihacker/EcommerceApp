package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Activities.HomePage;
import com.example.ecommerce.Helper.GetDataServices;
import com.example.ecommerce.Helper.RetrofitClientInstance;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.UserDetails;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btn_login,btn_signup,submit;
    ImageView img_login, img_signup;
    CardView card_login, card_signup;
    EditText mobile1,pass1;
    EditText mobile2,pass2,name1,email1;
    TextView forgetpass;
    Context ctx;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    String current_user;

    boolean isLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
     mAuth = FirebaseAuth.getInstance();
     db = FirebaseFirestore.getInstance();

        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }
        progressDialog = new ProgressDialog(this);
        btn_login=findViewById(R.id.btn_login);
        btn_signup=findViewById(R.id.btn_signup);
        img_login=findViewById(R.id.img_login);
        img_signup=findViewById(R.id.img_signup);
        card_login=findViewById(R.id.card_login);
        card_signup=findViewById(R.id.card_signup);
        mobile1=findViewById(R.id.mobile1);
        pass1=findViewById(R.id.pass1);
        submit=findViewById(R.id.submit);
        mobile2=findViewById(R.id.mobile2);
        email1=findViewById(R.id.email1);
        pass2=findViewById(R.id.pass2);
        name1=findViewById(R.id.name1);
        forgetpass=findViewById(R.id.forgetpass);

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,ForgetPassActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img_login.setVisibility(View.VISIBLE);
                        img_signup.setVisibility(View.GONE);
                        card_login.setVisibility(View.VISIBLE);
                        card_signup.setVisibility(View.GONE);
                        btn_login.setBackgroundResource(R.drawable.lgn_bg);
                        btn_signup.setBackgroundResource(R.drawable.sign_bg);
                        submit.setText("Login");
                    }
                }
        );
        btn_signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img_login.setVisibility(View.GONE);
                        img_signup.setVisibility(View.VISIBLE);
                        card_login.setVisibility(View.GONE);
                        card_signup.setVisibility(View.VISIBLE);
                        btn_login.setBackgroundResource(R.drawable.sign_bg);
                        btn_signup.setBackgroundResource(R.drawable.lgn_bg);
                        submit.setText("Signup");
                    }
                }
        );
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isLogin = true;
                        if(submit.getText().toString().equalsIgnoreCase("signup")){
//                            userRegistration(
//                                    name1.getText().toString(),
//                                    mobile2.getText().toString(),
//                                    email1.getText().toString(),
//                                    pass2.get
//
//                                    .Text().toString()
//
//                            );
                            String email = email1.getText().toString().trim();
                                  String pass =  pass2.getText().toString().trim();
                                  String user_name = name1.getText().toString().trim();
                                  String mobile = mobile2.getText().toString().trim();

                            if (TextUtils.isEmpty(user_name)) {
                                Toast.makeText(getApplicationContext(), "Enter user name!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(email)) {
                                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(mobile)) {
                                Toast.makeText(getApplicationContext(), "Enter Mobile Number!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(pass)) {
                                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (pass.length() < 6) {
                                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            firbaseRegister(email,pass, user_name, mobile);

                        }else{
                            String email = mobile1.getText().toString().trim();
                            String pass =  pass1.getText().toString().trim();

                            if (TextUtils.isEmpty(email)) {
                                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(pass)) {
                                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            firebaseLogin(email, pass);

                        }


                    }
                }
        );
    }

    public void firbaseRegister(final String email, final String pass, final String name, final String mobile)
    {

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Successfull Registerd", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Map<String, Object> user_data = new HashMap<>();
                            user_data.put("uid",user.getUid());
                            user_data.put("user_name",name);
                            user_data.put("mobile",mobile);
                            user_data.put("email", email);
                            user_data.put("password", pass);
                            user_data.put("dob", "");
                            user_data.put("gender", "");
                            user_data.put("img", "");
                            final FirebaseUser user1 = mAuth.getCurrentUser();
                            final String uid = user1.getUid();
                            DocumentReference _ref = FirebaseFirestore.getInstance().collection("users").document(uid);
                            _ref.set(user_data).isSuccessful();
//                            FirebaseFirestore.getInstance().collection("users").document(uid).set(user_data)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                Map<String,Object> list_size = new HashMap<>();
//                                                list_size.put("list_size",(long)0);
//                                                FirebaseFirestore.getInstance().collection("whishlist").document(uid)
//                                                        .collection("My_Wishlist").document()
//                                                        .set(list_size).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()){
//
//                                                        }else {
//                                                            String error=task.getException().getMessage();
//                                                            Toast.makeText(LoginPage.this, error, Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                });
//                                            }
//                                            else {
//                                                Toast.makeText(LoginPage.this, "error", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                            /////////////////////////////////
//                            db.collection("users").document(mAuth.getUid().toString())
//                                    .add(user_data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//
//                                        }
//                                    });
                            isLogin = false;
                            Intent i = new Intent(getApplicationContext(), LoginPage.class);
                            i.putExtra("email", email);     //????????????????
                            startActivity(i);
                        } else {
                            Log.w("not successful", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();

                    }
                });
    }

    public void firebaseLogin(final String email, String pass)
    {

       mAuth.signInWithEmailAndPassword(email, pass)
               .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(!task.isSuccessful()){
                           Toast.makeText(getApplicationContext(),"Email or Password Wrong",Toast.LENGTH_SHORT).show();
                       }
                       else
                       {

                           current_user = mAuth.getUid();

                           Log.d("UID", current_user);
                           FirebaseFirestore.getInstance().collection("users").document(current_user).get()
                                   .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                       @Override
                                       public void onSuccess(DocumentSnapshot documentSnapshot) {
                                           if(!documentSnapshot.exists())
                                           {
                                               Log.d("Not Exist","List is empty");
                                               return;
                                           }
                                           else
                                           {

                                              String name = documentSnapshot.get("user_name").toString();
                                               String email = documentSnapshot.get("email").toString();
                                               String phone = documentSnapshot.get("mobile").toString();
                                               String password = documentSnapshot.get("password").toString();
                                               SharedPrefManager.getInstance(getApplicationContext()).userLogin(current_user,name,email,phone,password);
                                               Intent i = new Intent(LoginPage.this, HomePage.class);
                                               startActivity(i);

                                               finish();

                                           }
                                       }
                                   });

                       }
                   }
               });
    }



//    public void userLogin(String mobile, final String password){
//
//        final ProgressDialog pd=ProgressDialog.show(LoginPage.this,"","Loading");
//        GetDataServices getDataServices= RetrofitClientInstance.getRetrofitInstance().create(GetDataServices.class);
//        Call<JsonArray> call=getDataServices.userLogin(mobile,password);
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                try {
//                    JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
//
//                    JSONObject jsonObject=jsonArray.getJSONObject(0);
//                    String res=jsonObject.getString("res");
//
//                    if(res.equalsIgnoreCase("success")){
//                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
//                       // String name=jsonObject1.getString("name");
//                        String name=jsonObject1.getString("Name");
//                        String id=jsonObject1.getString("id");
//                        //String mobile=jsonObject1.getString("mobile");
//                        String mobile=jsonObject1.getString("Mobile");
//                      //  String email=jsonObject1.getString("email");
//                        String email=jsonObject1.getString("Email");
//                      //  String password=jsonObject1.getString("password");
//                        String password=jsonObject1.getString("Password");
//
//                        UserDetails userDetails=new UserDetails(id,name,email,mobile,password);
//                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(userDetails);
//                        startActivity(new Intent(getApplicationContext(),HomePage.class));
//                        finish();
//                    }else {
//                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
//                    }
//                }catch (Exception e){
//                    Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
//                }
//                pd.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
//                pd.dismiss();
//
//            }
//        });
//
//    }
//
//
//    public void userRegistration(String username,String mobile, String email, String password){
//
//        final ProgressDialog pd=ProgressDialog.show(LoginPage.this,"","Loading");
//        GetDataServices getDataServices=RetrofitClientInstance.getRetrofitInstance().create(GetDataServices.class);
//        Call<JsonArray> call=getDataServices.userRegistration(username,mobile,email,password);
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                try {
//                    JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
//
//                    JSONObject jsonObject=jsonArray.getJSONObject(0);
//                    String res=jsonObject.getString("res");
//
//                    if(res.equalsIgnoreCase("success")){
//
//                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
//                       // String name=jsonObject1.getString("name");
//                        String name=jsonObject1.getString("Name");
//                        String id=jsonObject1.getString("id");
//                       // String mobile=jsonObject1.getString("mobile");
//                        String mobile=jsonObject1.getString("Mobile");
//                       // String email=jsonObject1.getString("email");
//                        String email=jsonObject1.getString("Email");
//                       // String password=jsonObject1.getString("password");
//                        String password=jsonObject1.getString("Password");
//
//                        UserDetails userDetails=new UserDetails(id,name,email,mobile,password);
//                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(userDetails);
//                        startActivity(new Intent(getApplicationContext(),HomePage.class));
//                        finish();
//
//                    }else{
//                        Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
//                    }
//                }catch (Exception e){
//                    Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
//                }
//                pd.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//
//                Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
//                pd.dismiss();
//
//            }
//        });
//    }
}


