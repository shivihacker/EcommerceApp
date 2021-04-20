package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgetPassActivity extends AppCompatActivity {
    EditText regist_email,regist_password,new_password;
    Button reset,back;
    FirebaseAuth auth;
    FirebaseUser user1;
    String uid,email,currentPass,newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        regist_email=findViewById(R.id.regist_email);
        regist_password=findViewById(R.id.regist_password);
        new_password=findViewById(R.id.new_password);
        reset=findViewById(R.id.btn_reset);
        back=findViewById(R.id.btn_back);


        auth= FirebaseAuth.getInstance();
        user1 = auth.getCurrentUser();
        uid = user1.getUid();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email=regist_email.getText().toString().trim();
                 currentPass=regist_password.getText().toString().trim();
                 newPass=new_password.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {

                    Toast.makeText(ForgetPassActivity.this, "Enter your registered Email Id", Toast.LENGTH_SHORT).show();
                    return;
                }




                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, currentPass);


//                final String newPass = "pass1234567";



                user1.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user1.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                DocumentReference _ref = FirebaseFirestore.getInstance().collection("users").document(uid);
                                                _ref.update("password",newPass).isSuccessful();
                                                Toast.makeText(ForgetPassActivity.this, "We have to sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();

                                                Log.d("password update", "Password updated");
                                            } else {
                                                Log.d("passwrod error", "Error password not updated");
                                            }
                                        }
                                    });
                                } else {
                                    Log.d("Errort Failed", "Error auth failed");
                                }
                            }
                        });











//                auth.sendPasswordResetEmail(email)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful())
//                                {
//
//                                    final FirebaseUser user1 = auth.getCurrentUser();
//                                    user1.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful())
//                                            {
//                                                }
//                                        }
//                                    });
//
//                                }
//                                else
//                                {
//                                    Toast.makeText(ForgetPassActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

            }
        });
    }
}