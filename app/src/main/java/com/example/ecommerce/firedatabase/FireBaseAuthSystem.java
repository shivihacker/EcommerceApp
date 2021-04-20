package com.example.ecommerce.firedatabase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ecommerce.Model.CategoryModel;
import com.example.ecommerce.Model.ModelProducts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class FireBaseAuthSystem {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    FirebaseStorage databaseRef;
    FirebaseFirestore firebaseFirestore;

    public FireBaseAuthSystem()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = firebaseAuth.getUid().toString();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    }


