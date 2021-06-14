package com.example.ecommerce.newAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.ecommerce.Adapter.ProductAdapter;
import com.example.ecommerce.Fragments.DetailsFragment;
import com.example.ecommerce.Fragments.MoreItemsFragment;
import com.example.ecommerce.Fragments.MyCartFragment;
import com.example.ecommerce.Fragments.ReviewItemsFragment;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Model.CategoryModel;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

public class NewItem extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment =null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String id,category_prod_id ;
    int recyclerviewPosition,categoryRecyclerviewPosition;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        firebaseFirestore=FirebaseFirestore.getInstance();

        tabLayout=findViewById(R.id.tablayout);
        frameLayout=findViewById(R.id.framelayout);
        ///////value from other intent
         Intent i = getIntent();
         id = i.getStringExtra("id");
        Intent recyclerintent=getIntent();
        recyclerviewPosition=recyclerintent.getIntExtra("recyclerview_position",0);
        Intent selection=getIntent();
        int select=selection.getIntExtra("SELECTION",0);

        Constaints.recycler_position=recyclerviewPosition;
        Constaints.product_id = id;
        Constaints.selection_position = select;
        Log.d("selection_categ",""+Constaints.selection_position);

        // filterProductData(id);
        Log.d("product_id", Constaints.product_id);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        fragment=new DetailsFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        DetailsFragment detailsFragment = new DetailsFragment();

//                        Bundle bundle = new Bundle();
//                        Log.d("ID before ",id);
//
//                        bundle.putString("id",id);
//                        Log.d("ID position",id);
//                       bundle.putInt("position",pos);

//                        detailsFragment.setArguments(bundle);
                        getFragment(new DetailsFragment());
                        break;
                    case 1:
                        getFragment(new MoreItemsFragment());
                        break;
                    case 2:
                        getFragment(new ReviewItemsFragment());
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void getFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }


    public void filterProductData(String id)
    {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_cart_icon,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.item_search_icon){
            //todo: search;
            return true;
        }else if (id==R.id.item_cart_icon){
            MyCartFragment myCartFragment = new MyCartFragment();
            //fragmentManager = getParentFragmentManager();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction =fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, myCartFragment);
//                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }else if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}