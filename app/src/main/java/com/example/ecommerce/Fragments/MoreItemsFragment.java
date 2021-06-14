package com.example.ecommerce.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Adapter.MoreItemAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Helper.WishlistSharedPref;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.ecommerce.Adapter.ProductAdapter.CATEGORY_LIST;
import static com.example.ecommerce.Adapter.ProductAdapter.HOME_LIST;

public class MoreItemsFragment extends Fragment {

    RecyclerView newvertical_more;
    String[] str={"","","","","",""};
    Context ctx;
    FirebaseFirestore firebaseFirestore;
    private ArrayList<ModelProducts> modelProductsList;
    private MoreItemAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_more_item, container, false);
       newvertical_more = view.findViewById(R.id.newvertical_more);
       firebaseFirestore=FirebaseFirestore.getInstance();

       int selection= Constaints.selection_position;
       Log.d("select_home",""+selection);

       modelProductsList=new ArrayList<>();
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        newvertical_more.setLayoutManager(new LinearLayoutManager(getActivity()));
        newvertical_more.setHasFixedSize(true);
        myAdapter=new MoreItemAdapter(getActivity(),modelProductsList);
//        newvertical_more.setAdapter(myAdapter);
        String p_collection= SharedPrefManager.getInstance(getActivity()).get_p_doc("p_collection");
        String p_document= SharedPrefManager.getInstance(getActivity()).get_p_doc("p_doc");
        String sub_p_collection= SharedPrefManager.getInstance(getActivity()).get_p_doc("sub_p_collection");


        if (selection==HOME_LIST){
            getMoreHomeProducts(p_collection,p_document,sub_p_collection);
        }else if (selection==CATEGORY_LIST){
            String sub_p_doc= SharedPrefManager.getInstance(getActivity()).get_p_doc("sub_p_doc");
            String super_sub_p_collection= SharedPrefManager.getInstance(getActivity()).get_p_doc("super_sub_p_collection");
            getMoreProducts(p_collection,p_document,sub_p_collection,sub_p_doc,super_sub_p_collection);
        }
        return view;


    }

    public void getMoreProducts(String p_collection,String p_doc, String sub_p_collection,String collection_type,String products) {
        firebaseFirestore.collection(p_collection)
                .document(p_doc).collection(sub_p_collection).document(collection_type).collection(products).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ModelProducts modelProducts = new ModelProducts(
                                        documentSnapshot.getString("id"),
                                        documentSnapshot.getString("p_name"),
                                        documentSnapshot.getString("p_rating"),
                                        documentSnapshot.getString("p_price"),
                                        documentSnapshot.getString("p_date"),
                                        documentSnapshot.getString("p_desc"),
                                        documentSnapshot.getString("p_title"),
                                        documentSnapshot.getString("img"),
                                        documentSnapshot.getString("p_brand"),
                                        documentSnapshot.getString("p_review"));
                                modelProductsList.add(modelProducts);

                                Log.d("iiiiddd", modelProducts.getCaregoryId());
                                Log.d("nameeee", modelProducts.getName());
                                Log.d("priceee", modelProducts.getPrice());
                            }
                            newvertical_more.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
//                            productAdapter=new ProductAdapter(modelProductsList,0);
//                            grid.setAdapter(productAdapter);
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void getMoreHomeProducts(String p_collection,String p_doc, String sub_p_collection) {
        firebaseFirestore.collection(p_collection)
                .document(p_doc).collection(sub_p_collection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ModelProducts modelProducts = new ModelProducts(
                                        documentSnapshot.getString("id"),
                                        documentSnapshot.getString("p_name"),
                                        documentSnapshot.getString("p_rating"),
                                        documentSnapshot.getString("p_price"),
                                        documentSnapshot.getString("p_date"),
                                        documentSnapshot.getString("p_desc"),
                                        documentSnapshot.getString("p_title"),
                                        documentSnapshot.getString("img"),
                                        documentSnapshot.getString("p_brand"),
                                        documentSnapshot.getString("p_review"));
                                modelProductsList.add(modelProducts);

                                Log.d("iiiiddd", modelProducts.getCaregoryId());
                                Log.d("nameeee", modelProducts.getName());
                                Log.d("priceee", modelProducts.getPrice());
                            }
                            newvertical_more.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
//                            productAdapter=new ProductAdapter(modelProductsList,0);
//                            grid.setAdapter(productAdapter);
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
