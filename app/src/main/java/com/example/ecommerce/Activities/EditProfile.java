package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    RadioGroup radioGroup;
    ImageView profileImgView;
    RadioButton radio_btn1 , radio_btn2, rb;
    RadioButton radioButton;
    EditText edit_name,edit_email,edit_mob,edit_dob;
    Button saved_profile;
    Uri imageUri;
    byte[] bitmapbytes;
    FirebaseAuth auth;
    String fname,femail,fmob,fimage;
   Context ctx;
  //  FirebaseFirestore fstore;
     FirebaseUser user;
     String uid;
     StorageReference storageReference;
     //       ProgressDialog progressDialog;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        radio_btn1=findViewById(R.id.btn1_sex);
        radio_btn2=findViewById(R.id.btn2_sex);
        radioGroup=findViewById(R.id.radio_group);
        profileImgView=findViewById(R.id.profileimgview);
        edit_name=findViewById(R.id.fragName);
        edit_email=findViewById(R.id.frag_email);
        edit_mob=findViewById(R.id.frag_mob);
        edit_dob=findViewById(R.id.frag_dob);
        saved_profile=findViewById(R.id.saved_profile);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inflate the layout for this fragment
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton)findViewById(checkedId);
                value = rb.getText().toString();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        //Take data from Firebase
        uid = user.getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get()
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
                            fname = documentSnapshot.get("user_name").toString();
                            femail=documentSnapshot.get("email").toString();
                            fmob=documentSnapshot.get("mobile").toString();
                            fimage=documentSnapshot.get("img").toString();
                            edit_name.setText(fname);
                            edit_email.setText(femail);
                            edit_mob.setText(fmob);
                            setImage();

                        }
                    }
                });


        profileImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opengallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengallery,1000);
            }
        });

        saved_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                DocumentReference _ref = FirebaseFirestore.getInstance().collection("users").document(uid);

                String dob = edit_dob.getText().toString().trim();
                Map<String,Object> edited=new HashMap<>();
                edited.put("dob",dob);
                edited.put("gender",value);

                Toast.makeText(getApplicationContext(), "Profile update", Toast.LENGTH_SHORT).show();
                if(dob!=null && value!=null) {
                    _ref.update(edited).isSuccessful();
                }

//                user.updateEmail("email").addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
////                        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000 && data.getData() != null){
            if (resultCode== Activity.RESULT_OK){
                imageUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
                bitmapbytes = byteArrayOutputStream.toByteArray();

uploadImagetoFirebase();
            }
        }
    }

    private void uploadImagetoFirebase() {
        String filePath = "customer_images/"+"account_image_"+uid;
        storageReference = FirebaseStorage.getInstance().getReference().child(filePath);
        storageReference.putBytes(bitmapbytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri downloadUri = uriTask.getResult();
                if(uriTask.isSuccessful())
                {
                    DocumentReference _ref = FirebaseFirestore.getInstance().collection("users").document(uid);
                    _ref.update("img", downloadUri.toString()).isSuccessful();
                    Glide.with(getApplicationContext()).load(downloadUri.toString()).optionalFitCenter().circleCrop().into(profileImgView);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




//
//        storageReference = FirebaseStorage.getInstance().getReference().child("users").child(uid);
//        StorageTask uploadTask = storageReference.putFile(imageuri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
//                if(progress == 100)
//                {
//
//                }
//             }
//        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onPaused(@NonNull UploadTask.TaskSnapshot snapshot) {
//                System.out.println("Upload is paused");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                System.out.println("Image Failure");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                String uri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
//                Log.d("uri",uri);
//                if(uri != null)
//                {
//                    Map<String, Object> user_data = new HashMap<>();
//                    user_data.put("uid",user.getUid());
//                    user_data.put("user_name",name);
//                    user_data.put("mobile",mobile);
//                    user_data.put("email", email);
//                    user_data.put("password", pass);
           //         Constaints.profile_img = uri;
//                    DocumentReference _ref = FirebaseFirestore.getInstance().collection("users").document(uid);
//                    _ref.update("img", uri).isSuccessful();
//                    try {
//                        Glide.with(getApplicationContext()).load(uri).into(profileImgView);
//                    }
//                    catch (Exception e)
//                    {
//                        Toast.makeText(getApplicationContext(),"no Image", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//            }
//        });







//        final StorageReference profRef= storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
//        profRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                profRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into( profileImgView);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ctx, "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }
    private void setImage() {
        Glide.with(getApplicationContext()).load(fimage).optionalFitCenter().circleCrop().into(profileImgView);
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