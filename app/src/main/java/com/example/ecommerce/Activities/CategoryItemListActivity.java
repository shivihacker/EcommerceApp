package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ecommerce.Adapter.CategoryAdapter;
import com.example.ecommerce.Adapter.ProductAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.CategoryModel;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.Model.SliderModel;
import com.example.ecommerce.NewAdapter.SliderHomeAdapter;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class CategoryItemListActivity extends AppCompatActivity {
    private ViewPager viewpageBanner;
    private CircleIndicator circleIndicator;
    private RecyclerView categoryRecycler;
    private ArrayList<ModelProducts> modelProductsList;
    ArrayList<SliderModel> sliderModelList;
    SliderHomeAdapter sliderHomeAdapter;
    ProductAdapter productAdapter;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_list);
        viewpageBanner=findViewById(R.id.category_viewpage);
        circleIndicator=findViewById(R.id.category_indicator);
        categoryRecycler=findViewById(R.id.category_item_list_recyclerview);
        firebaseFirestore=FirebaseFirestore.getInstance();

        Log.d("category_position",""+ Constaints.category_position);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Intent mob_pos=getIntent();
        int pos = i.getIntExtra("position",0);
        int mobile_position=mob_pos.getIntExtra("mobile_position",0);
        Log.d("Position_Of_Element111", "" + pos);
        Log.d("Position_Of_Mobile", "" + mobile_position);
        Constaints.mobile_category_position=mobile_position;
        Constaints.category_product_position=pos;

        sliderModelList=new ArrayList<SliderModel>();
        //list=new ArrayList<>();
        sliderHomeAdapter=new SliderHomeAdapter(sliderModelList);
        viewpageBanner.setClipToPadding(false);
        viewpageBanner.setPageMargin(20);
        viewpageBanner.setAdapter(sliderHomeAdapter);
        sliderBanner();
        circleIndicator.setViewPager(viewpageBanner);
        Log.d("jgjhjk",""+Constaints.category_position);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        categoryRecycler.setLayoutManager(layoutManager);
        categoryRecycler.setHasFixedSize(true);
        modelProductsList=new ArrayList<>();
        productAdapter = new ProductAdapter(getApplication(),modelProductsList, 3);
        categoryRecycler.setAdapter(productAdapter);
        if (mobile_position==1){
            Log.d("mobilebaifbaa",""+Constaints.category_position);
            mobileProduct();
        }else if (Constaints.category_position==2){
            if (pos==0){
                getProducts("FASHION","fashion_category","mens");
            }else if (pos==1){
                getProducts("FASHION","fashion_category","womens");
            }else if (pos==2) {
                getProducts("FASHION", "fashion_category", "kids");
            }
        }else if (Constaints.category_position==3){
            if (pos==0){
                getProducts("ELECTRONICS","electronics_category","tv");
            }else if (pos==1){
                getProducts("ELECTRONICS","electronics_category","washingmachine");
            }else if (pos==2) {
                getProducts("ELECTRONICS", "electronics_category", "fridge");
            }else if (pos==3) {
                getProducts("ELECTRONICS", "electronics_category", "computer_accessories");
            }
        }else if (Constaints.category_position==4){
            if (pos==0){
                getProducts("HOME APPLIANCES","home_category","home_hygiene");
            }else if (pos==1){
                getProducts("HOME APPLIANCES","home_category","kitchen");
            }else if (pos==2) {
                getProducts("HOME APPLIANCES", "home_category", "decorelighting");
            }
        }else if (Constaints.category_position==5){
            if (pos==0){
                getProducts("GADGETS","gadgets_category","mobile_accessories");
            }else if (pos==1){
                getProducts("GADGETS","gadgets_category","smart_watch");
            }else if (pos==2) {
                getProducts("GADGETS", "gadgets_category", "camera");
            }else if (pos==3) {
                getProducts("GADGETS", "gadgets_category", "styling devices");
            }
        }else if (Constaints.category_position==6){
            if (pos==0){
                getProducts("BEAUTY PRODUCTS","Beauty_category","skin_care");
            }else if (pos==1){
                getProducts("BEAUTY PRODUCTS","Beauty_category","makeup");
            }else if (pos==2) {
                getProducts("BEAUTY PRODUCTS", "Beauty_category", "hair_care");
            }else if (pos==3) {
                getProducts("BEAUTY PRODUCTS", "Beauty_category", "fragnance_product");
            }
        }else if (Constaints.category_position==7){
            if (pos==0){
                getProducts("TOYS & GAMES","toys_games_category","for_boys");
            }else if (pos==1){
                getProducts("TOYS & GAMES","toys_games_category","for_girls");
            }else if (pos==2) {
                getProducts("TOYS & GAMES", "toys_games_category", "for_infants");
            }
        }else if (Constaints.category_position==8){
            if (pos==0){
                getProducts("SPORTS","sports_category","sports");
            }else if (pos==1){
                getProducts("SPORTS","sports_category","fitness");
            }else if (pos==2) {
                getProducts("SPORTS", "sports_category", "nutrition");
            }
        }else if (Constaints.category_position==9){
            if (pos==0){
                getProducts("FURNITURE","furniture_category","wardrobe");
            }else if (pos==1){
                getProducts("FURNITURE","furniture_category","bed_matresses");
            }else if (pos==2) {
                getProducts("FURNITURE", "furniture_category", "table_chairs");
            }else if (pos==3) {
                getProducts("FURNITURE", "furniture_category", "sofas");
            }
        }
    }
    public void sliderBanner(){
        firebaseFirestore.collection("Banner").document("banner_slider").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            ArrayList<SliderModel> sliderModels = (ArrayList<SliderModel>) documentSnapshot.get("my_img");
                            for (int i = 0; i<sliderModels.size();i++)
                            {
                                sliderModelList.add(sliderModels.get(i));
                            }
                            Log.d("banner images", sliderModelList.toString());
                            sliderHomeAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void mobileProduct() {
        firebaseFirestore.collection("CATEGORIES")
                .document("MOBILES").collection("products").get()
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
                                modelProductsList.add(modelProducts);
                                Log.d("name", modelProducts.getImage());
                            }
                            SharedPrefManager.getInstance(getApplicationContext()).setMoreHomeProduct("CATEGORIES","MOBILES","products");                            productAdapter.notifyDataSetChanged();
//                            productAdapter=new ProductAdapter(modelProductsList,0);
//                            grid.setAdapter(productAdapter);
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getProducts(final String p_doc, final String p_collection, final String collection_type) {
        firebaseFirestore.collection("CATEGORIES")
                .document(p_doc).collection(p_collection).document(collection_type).collection("products").get()
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
                                    modelProductsList.add(modelProducts);
                                Log.d("iiiiddd", modelProducts.getCaregoryId());
                                Log.d("nameeee", modelProducts.getName());
                                Log.d("priceee", modelProducts.getPrice());
                            }
                            SharedPrefManager.getInstance(getApplicationContext()).setProductPath("CATEGORIES",p_doc,
                                    p_collection,collection_type,"products");
                            productAdapter.notifyDataSetChanged();
//                            productAdapter=new ProductAdapter(modelProductsList,0);
//                            grid.setAdapter(productAdapter);
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.main_search_icon){
            //todo: search;
            return true;
        }else if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}