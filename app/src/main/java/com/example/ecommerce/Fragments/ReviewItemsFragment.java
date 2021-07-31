package com.example.ecommerce.Fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.SliderModel;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static com.example.ecommerce.Adapter.ProductAdapter.CATEGORY_LIST;
import static com.example.ecommerce.Adapter.ProductAdapter.HOME_LIST;

public class ReviewItemsFragment extends Fragment {
    TextView more_review,ratingTextView,totalreview;
    private RatingBar ratingStar;
    private int selection;
    private String  w_rating,w_review;
    FirebaseFirestore firebaseFirestore;

    public ReviewItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_reviewitems, container, false);
        // Inflate the layout for this fragment
        totalreview=view.findViewById(R.id.reviewitem_totalreview);
        ratingStar=view.findViewById(R.id.reviewitem_ratingbar);
        ratingTextView=view.findViewById(R.id.reviewitem_ratingtextview);
        firebaseFirestore= FirebaseFirestore.getInstance();
        selection= Constaints.selection_position;

        final String p_document= SharedPrefManager.getInstance(getActivity()).get_p_doc("p_doc");
        final String sub_p_collection= SharedPrefManager.getInstance(getActivity()).get_p_doc("sub_p_collection");

        if (selection==HOME_LIST){
            filterProductData(p_document,sub_p_collection,Constaints.product_id);
        }else if (selection==CATEGORY_LIST){
            String super_sub_p_collection= SharedPrefManager.getInstance(getActivity()).get_p_doc("super_sub_p_collection");
            categoryProductData(p_document,sub_p_collection,super_sub_p_collection,Constaints.product_id);
        }

        ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingTextView.setText(""+rating);
                if (selection==HOME_LIST){
                    updateDataHome(rating,p_document,sub_p_collection,Constaints.product_id);
                }else if (selection==CATEGORY_LIST) {
                    String super_sub_p_collection= SharedPrefManager.getInstance(getActivity()).get_p_doc("super_sub_p_collection");
                    updateData(rating,p_document,sub_p_collection,super_sub_p_collection,Constaints.product_id);
                }
            }
        });

        return view; }

    /////////////////////homeFragment Data////////////////
    public void filterProductData(String p_doc, String p_collection,String id) {
        firebaseFirestore.collection("CATEGORIES").document(p_doc).collection(p_collection).document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();

                            w_rating = documentSnapshot.getString("p_rating");
                            ratingTextView.setText(w_rating);

                            w_review = documentSnapshot.getString("p_review");
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /////////////////////homeFragment Data////////////////

    ///////////////////Category Other Product Data/////////////////////////////
    public void categoryProductData(String p_doc, String p_collection,String collection_type,String id) {
        firebaseFirestore.collection("CATEGORIES").document(p_doc).collection(p_collection).document(collection_type)
                .collection("products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    w_rating = documentSnapshot.getString("p_rating");
                    ratingTextView.setText(w_rating);

                    w_review = documentSnapshot.getString("p_review");
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    ///////////////////Category Other Product Data/////////////////////////////

    public void updateData(float rating,String p_doc, String p_collection,String collection_type,String id){
            DocumentReference reference=firebaseFirestore.collection("CATEGORIES").document(p_doc).collection(p_collection).document(collection_type)
                    .collection("products").document(id);
            reference.update("p_rating",""+rating).isSuccessful();

    }
    public void updateDataHome(float rating,String p_doc, String p_collection,String id){
        DocumentReference reference=firebaseFirestore.collection("CATEGORIES").document(p_doc).collection(p_collection).document(id);
        reference.update("p_rating",""+rating).isSuccessful();

    }

}
