package com.example.ecommerce.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Adapter.CategoryAdapter;
import com.example.ecommerce.Adapter.ViewpageAdapter;
import com.example.ecommerce.Fragments.HomeFragment;
import com.example.ecommerce.Fragments.MyCartFragment;
import com.example.ecommerce.Fragments.MyWishListFragment;
import com.example.ecommerce.Fragments.OrderFragment;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.CategoryModel;
import com.example.ecommerce.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ecommerce.firedatabase.FireBaseAuthSystem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skydoves.elasticviews.ElasticButton;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int HOME_FRAGMENT=0;
    private static final int CART_FRAGMENT=1;
    private static final int ORDER_FRAGMENT=2;
    private static final int WISHLIST_FRAGMENT=3;

    private ImageView nav_imageView;
    private TextView nav_email;
    private TextView nav_username;
    String uid,profile_img;
    private ImageView actionBarLogo;
    private static int currentFragment=-1;
    private NavigationView navigationView;
    View hView;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

      Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        actionBarLogo =findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar,(R.string.open_navigation_drawer),(R.string.close_navigation_drawer));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        hView=navigationView.getHeaderView(0);
        nav_username=hView.findViewById(R.id.nav_username);
        nav_email=hView.findViewById(R.id.nav_email);
        nav_imageView=hView.findViewById(R.id.nav_imageView);
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
                                nav_username.setText(documentSnapshot.get("user_name").toString());
                                nav_email.setText(documentSnapshot.get("email").toString());
                                profile_img = documentSnapshot.get("img").toString();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Gender field none", Toast.LENGTH_SHORT).show();
                            }
                            Glide.with(getApplicationContext()).load(profile_img).optionalCircleCrop().into(nav_imageView);
                        }
                    }
                });
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getMenu().getItem(0).setChecked(true);
//
//        frameLayout=findViewById(R.id.framelayout);
        setFragment(new HomeFragment(),HOME_FRAGMENT);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        firebaseFirestore=FirebaseFirestore.getInstance();
    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo!=currentFragment){
            currentFragment=fragmentNo;
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            if (currentFragment==HOME_FRAGMENT) {
                super.onBackPressed();
            }else {
                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragment(),HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment==HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.home_page, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.homepage_notification_icon){
            return true;
        }else if (id==R.id.homepage_cart_icon){
          //  gotoFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
            setFragment(new MyCartFragment(),CART_FRAGMENT);
            return true;
        }else  if (id==R.id.homepage_account_icon){
            ////////////////////////
            if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }else {

                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
            ///////////////////////////
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.nav_home){
            actionBarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new HomeFragment(),HOME_FRAGMENT);

        }else
       if (id==R.id.nav_your_order){
            setFragment(new OrderFragment(),ORDER_FRAGMENT);
           actionBarLogo.setVisibility(View.GONE);
           getSupportActionBar().setDisplayShowTitleEnabled(true);
           getSupportActionBar().setTitle("My Order");

        }else if (id==R.id.nav_my_cart){
//            gotoFragment("My Cart"+new MyCartFragment(),CART_FRAGMENT);
           setFragment(new MyCartFragment(),CART_FRAGMENT);
           actionBarLogo.setVisibility(View.GONE);
           getSupportActionBar().setDisplayShowTitleEnabled(true);
           getSupportActionBar().setTitle("My Cart");
          // invalidateOptionsMenu();

        } else if (id==R.id.nav_my_wishlist){
           actionBarLogo.setVisibility(View.GONE);
           getSupportActionBar().setDisplayShowTitleEnabled(true);
           getSupportActionBar().setTitle("My Wishlist");
            setFragment(new MyWishListFragment(),WISHLIST_FRAGMENT);
        }
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
            return false;
    }

//    private void gotoFragment(String title, int fragmentNo) {
//        actionBarLogo.setVisibility(View.GONE);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle(title);
//        invalidateOptionsMenu();
//        setFragment(new MyCartFragment(),fragmentNo);
//        if (fragmentNo==CART_FRAGMENT){
//            navigationView.getMenu().getItem(3).setChecked(true);
//        }
//    }



}
