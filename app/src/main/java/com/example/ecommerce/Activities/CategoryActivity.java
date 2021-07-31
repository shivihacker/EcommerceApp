package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Adapter.CategoryAdapter;
import com.example.ecommerce.Adapter.ViewpageAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Model.CategoryModel;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryItemRecyclerview;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelsList;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        int pos = i.getIntExtra("position",0);
        Constaints.category_position=pos;
        Log.d("Position Of Element", "" + pos);

        firebaseFirestore=FirebaseFirestore.getInstance();

        ////////viewPager///////////
        ViewpageAdapter viewpageAdapter=new ViewpageAdapter(getApplicationContext());
//        viewpage.setAdapter(viewpageAdapter);
//        indicator.setViewPager(viewpage);
        categoryItemRecyclerview=findViewById(R.id.activity_category_item_recyclerview);
        RecyclerView.LayoutManager layoutManager1=new GridLayoutManager(getApplicationContext(),3);
        categoryItemRecyclerview.setLayoutManager(layoutManager1);
        categoryItemRecyclerview.setHasFixedSize(true);
        categoryModelsList=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(categoryModelsList,1);
        categoryItemRecyclerview.setAdapter(categoryAdapter);
        if (pos==2){
            getCategory("FASHION","fashion_category");
        }else if (pos==3){
            getCategory("ELECTRONICS","electronics_category");
        }else if (pos==4){
            getCategory("HOME APPLIANCES","home_category");
        }else if (pos==5){
            getCategory("GADGETS","gadgets_category");
        }else if (pos==6){
            getCategory("BEAUTY PRODUCTS","Beauty_category");
        }else if (pos==7){
            getCategory("TOYS & GAMES","toys_games_category");
        }else if (pos==8){
            getCategory("SPORTS","sports_category");
        }else if (pos==9){
            getCategory("FURNITURE","furniture_category");
        }


    }
    public void getCategory(String category_doc,String product_collec){
        firebaseFirestore.collection("CATEGORIES").document(category_doc).collection(product_collec)
                .orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                categoryModelsList.add(new CategoryModel(documentSnapshot.get("categoryIcon")
                                        .toString(),documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
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