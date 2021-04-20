package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Adapter.ProfileAdapter;
import com.example.ecommerce.Helper.SharedPrefManager;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.skydoves.elasticviews.ElasticButton;
import com.squareup.picasso.Picasso;

public class ProfilePage extends AppCompatActivity {
    RecyclerView profile_vertical;
    String[] str = new String[]{"", "", "", "", "", ""};
    String name, email, num, dob, gender;
    TextView uName, uemail, umobile, ugender, udob;
    FirebaseAuth auth;
    Button editprof;
    LinearLayout logout, del_account;
    ImageView img;
    //   StorageReference storageReference;
    String uid, profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        profile_vertical = findViewById(R.id.profile_vertical);
        logout = findViewById(R.id.logout);
        del_account = findViewById(R.id.dele_account);
        uName = findViewById(R.id.userName);
        uemail = findViewById(R.id.user_email);
        umobile = findViewById(R.id.user_mob);
        ugender = findViewById(R.id.user_gender);
        udob = findViewById(R.id.user_dob);
        editprof = findViewById(R.id.editprof);
        img = findViewById(R.id.prfile_page_img);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Take data from Firebase
        uid = user.getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            Log.d("Not Exist", "List is empty");
                            return;
                        } else {
                            try {
                                name = documentSnapshot.get("user_name").toString();
                                email = documentSnapshot.get("email").toString();
                                num = documentSnapshot.get("mobile").toString();
                                dob = documentSnapshot.get("dob").toString();
                                gender = documentSnapshot.get("gender").toString();
                                profile_img = documentSnapshot.get("img").toString();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Gender field none", Toast.LENGTH_SHORT).show();
                            }
                            uName.setText(name);
                            uemail.setText(email);
                            umobile.setText(num);
                            ugender.setText(gender);
                            udob.setText(dob);

                            setImage();


                        }
                    }
                });

        editprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilePage.this, EditProfile.class);
                startActivity(i);
            }
        });
        del_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    deleteAccount();
//
                }
            }
        });

        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // signout();
                        Toast.makeText(ProfilePage.this, "Signout Successfull", Toast.LENGTH_SHORT).show();
                        SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
                        sharedPrefManager.logout();
                        auth.signOut();

                    }

                    //   private void signout() {

                    // }
                }
        );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        profile_vertical.setLayoutManager(layoutManager);
        profile_vertical.setHasFixedSize(true);
        ProfileAdapter myAdapter = new ProfileAdapter(str, getApplicationContext());
        profile_vertical.setAdapter(myAdapter);

    }

    private void deleteAccount() {
        Log.d("Del", "ingreso a deleteAccount");
        try{
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("Del", "OK! Works fine!");
                        DocumentReference _ref = FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid());
                        _ref.delete().isSuccessful();
                        SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
                        sharedPrefManager.logout();
                        Toast.makeText(ProfilePage.this, "Delete Account Successfull", Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Del", "Ocurrio un error durante la eliminación del usuario", e);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setImage() {
        Glide.with(this).load(profile_img).optionalCircleCrop().into(img);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_item_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.menu_home){
            //todo: search;
            return true;
        }else if (id==R.id.menu_wishlist){
            return true;
        }else  if (id==R.id.menu_order){
            return true;
        }else  if (id==R.id.menu_cart){
            return true;
        }else if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}