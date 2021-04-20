package com.example.ecommerce.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Adapter.CategoryAdapter;
import com.example.ecommerce.Adapter.ProductAdapter;
import com.example.ecommerce.Model.CategoryModel;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.Model.SliderModel;
import com.example.ecommerce.NewAdapter.SliderHomeAdapter;
import com.example.ecommerce.R;
import com.example.ecommerce.utils.MyLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private RecyclerView categoryRecyclerview,grid,vertical,horizontal;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelsList;
    ArrayList<SliderModel> sliderModelList;
    // private ArrayList<List<Map<String, Object>>> list;
    SliderHomeAdapter sliderHomeAdapter;
//    int currentpage=2;
    private ArrayList<ModelProducts> modelProductsList;
    private ArrayList<ModelProducts> modelProductsList1;
    private ArrayList<ModelProducts> modelProductsList2;
    ProductAdapter productAdapter;
    TextView viewmore,viewmore1,viewmore2;
    CircleIndicator indicator;
    ViewPager viewpage;
    FirebaseFirestore firebaseFirestore;
//    String[] str=new String[]{"","","","","",""};
//    int[] str2=new int[]{R.drawable.gulmohar1,R.drawable.gulmohar2,R.drawable.gulmohar3,R.drawable.gulmohar4,R.drawable.gulmohar5};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        viewpage=view.findViewById(R.id.viewpage);
        indicator=view.findViewById(R.id.indicator);
        grid=view.findViewById(R.id.grid);
        vertical=view.findViewById(R.id.vertical);
        horizontal=view.findViewById(R.id.horizontal);
        categoryRecyclerview=view.findViewById(R.id.category_recyclerview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerview.setLayoutManager(linearLayoutManager);
        categoryModelsList=new ArrayList<CategoryModel>();
        categoryAdapter=new CategoryAdapter(categoryModelsList,0);
        categoryRecyclerview.setAdapter(categoryAdapter);
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                              categoryModelsList.add(new CategoryModel(documentSnapshot.get("icon")
                                      .toString(),documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        ////////viewPager///////////
//        ViewpageAdapter viewpageAdapter=new ViewpageAdapter(getActivity());
//        viewpage.setAdapter(viewpageAdapter);
//        indicator.setViewPager(viewpage);
        sliderModelList=new ArrayList<SliderModel>();
        //list=new ArrayList<>();
        sliderHomeAdapter=new SliderHomeAdapter(sliderModelList);
        viewpage.setClipToPadding(false);
        viewpage.setPageMargin(20);
        viewpage.setAdapter(sliderHomeAdapter);
        sliderBanner();
        indicator.setViewPager(viewpage);

        ///////viewPager////////////
        RecyclerView.LayoutManager layoutManager1=new GridLayoutManager(getActivity(),2);
        grid.setLayoutManager(layoutManager1);
        grid.setHasFixedSize(true);
        modelProductsList=new ArrayList<>();
        getProducts(0,"MOBILES","mobile_products");

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal.setLayoutManager(layoutManager2);
        horizontal.setHasFixedSize(true);
        modelProductsList1=new ArrayList<>();
        getProducts(1,"BEAUTY PRODUCTS","products");

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        vertical.setLayoutManager(layoutManager);
        vertical.setHasFixedSize(true);
        modelProductsList2=new ArrayList<>();
        getProducts(2,"SPORTS","sports_products");
      return view;
    }

    ///////////////viewpage function//////////////
   public void sliderBanner(){
        firebaseFirestore.collection("Banner").document("banner_slider").get()
               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()) {
                           DocumentSnapshot documentSnapshot = task.getResult();
                           ArrayList<SliderModel> sliderModels = (ArrayList<SliderModel>) documentSnapshot.get("my_img");

                           // sliderModelList.add(new SliderModel((String) documentSnapshot.get("b_image")));
                           for (int i = 0; i<sliderModels.size();i++)
                           {
                               sliderModelList.add(sliderModels.get(i));

                           }


                           //sliderModels.get(0);
//                           String[] image = {};
//                          int a = documentSnapshot.get("my_img").toString().length();
//                           for (int i = 0; i<((ArrayList<SliderModel>) documentSnapshot.get("my_img")).toArray().length; i++){
//                               sliderModelList.
//                           }
////                           sliderModelList.add(sliderModels.get());

                           Log.d("banner images", sliderModelList.toString());


//                           List<Map<String, Object>> users = (List<Map<String, Object>>) documentSnapshot.get("b_img");
//                           list.add(users);
                           sliderHomeAdapter.notifyDataSetChanged();
                       } else {
                           String error = task.getException().getMessage();
                           Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                       }
                   }
               });
   }
   ///////////////viewpage function//////////////

    public void getProducts(final int pos, String p_doc, String p_collection)
    {
        firebaseFirestore.collection("CATEGORIES")
                .document(p_doc).collection(p_collection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ModelProducts modelProducts = new ModelProducts(documentSnapshot.getString("id"),
                                        documentSnapshot.getString("p_name"),
                                        documentSnapshot.getString("p_rating"),
                                        documentSnapshot.getString("p_price"),
                                        documentSnapshot.getString("p_date"),
                                        documentSnapshot.getString("p_desc"),
                                        documentSnapshot.getString("p_title"),
                                        documentSnapshot.getString("img"),
                                        documentSnapshot.getString("p_brand"),
                                        documentSnapshot.getString("p_review"));
                                if (pos == 0) {
                                    modelProductsList.add(modelProducts);
                                }
                                if (pos == 1) {
                                    modelProductsList1.add(modelProducts);
                                }
                                if (pos == 2) {
                                    modelProductsList2.add(modelProducts);

                                }
                                Log.d("name", modelProducts.getImage());
                            }
//                            productAdapter=new ProductAdapter(modelProductsList,0);
//                            grid.setAdapter(productAdapter);
                            switch (pos) {
                                case 0:
                                    productAdapter = new ProductAdapter(modelProductsList, 0);
                                    grid.setAdapter(productAdapter);
                                    break;
                                case 1:
                                    productAdapter = new ProductAdapter(modelProductsList1, 1);
                                    horizontal.setAdapter(productAdapter);
                                    break;
                                case 2:
                                    productAdapter = new ProductAdapter(modelProductsList2, 2);
                                    vertical.setAdapter(productAdapter);
                                    break;
                            }
                        }
                    }
                });
    }


}