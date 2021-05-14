package com.example.ecommerce.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.Activities.DeliveryActivity;
import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.ItemShowViewPageAdap;
import com.example.ecommerce.Adapter.MyWishlistAdapter;
import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.Model.ModelProducts;
import com.example.ecommerce.Model.MyWishlistModel;
import com.example.ecommerce.Model.SliderModel;
import com.example.ecommerce.NewAdapter.SliderHomeAdapter;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skydoves.elasticviews.ElasticButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class DetailsFragment extends Fragment {
    // RecyclerView newitem_horizontal;
    private CircleIndicator newindicator;
    private ViewPager newviewpage;
    private FloatingActionButton addToWishlist;
    private ElasticButton buyNow, addToCart;
    private static boolean ALREADY_ADDED_TO_WISHLIST=false;
    List<String> wishlist;
    List<MyWishlistModel> wishlistModelList;
    ArrayList<SliderModel> sliderModelList;
    SliderHomeAdapter sliderHomeAdapter;
    DocumentSnapshot documentSnapshot;
    String mid, w_price, w_title,w_rating, w_img,w_review;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    //List<ViewPager> viewPagerList;
    //ArrayList<String> list;
    private TextView price, product_name, rating,review,description;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int counter=0;
    String i;
    int colourGrey,colourRed;
    public DetailsFragment() {

    }

    //ItemShowViewPageAdap viewpageAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        newviewpage=view.findViewById(R.id.newviewpage);
        addToWishlist=view.findViewById(R.id.add_to_wishlist);
        buyNow=view.findViewById(R.id.detail_buy_now);
        addToCart=view.findViewById(R.id.detail_add_cart);
        newindicator=view.findViewById(R.id.newindicator);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        Constaints.current_user=mAuth.getUid();
        Log.d("currentuser",Constaints.current_user);
        //list = new ArrayList<>();
        getWishlistData();
        colorData();

        price = view.findViewById(R.id.price);
        description = view.findViewById(R.id.detail_frag_description);
        product_name = view.findViewById(R.id.p_name);
        rating = view.findViewById(R.id.rating);
        review = view.findViewById(R.id.review);

        //////////////////Colour/////////////////////
        colourGrey=Color.parseColor("#9e9e9e");
        colourRed=Color.RED;
        Constaints.greyColor=colourGrey;
        Constaints.redColor=colourRed;
        //////////////////Colour/////////////////////

        /////////////////viewpager////////////////////
        sliderModelList=new ArrayList<SliderModel>();
        //list=new ArrayList<>();
        sliderHomeAdapter=new SliderHomeAdapter(sliderModelList);
        newviewpage.setClipToPadding(false);
        newviewpage.setPadding(0,10,0,10);
        newviewpage.setAdapter(sliderHomeAdapter);
        //sliderBanner();
       // newindicator.setViewPager(newviewpage);
        /////////////////viewpager////////////////////
        wishlist=new ArrayList<>();
        wishlistModelList=new ArrayList<>();
        if (Constaints.recycler_position==0) {
            Log.d("recycler_position",""+Constaints.recycler_position);
            filterProductData("MOBILES", "mobile_products", Constaints.product_id);
        }
        else if (Constaints.recycler_position==1){
            Log.d("recycler_position",""+Constaints.recycler_position);
            filterProductData("BEAUTY PRODUCTS", "products", Constaints.product_id);

        }
        else if (Constaints.recycler_position==2) {
            Log.d("recycler_position",""+Constaints.recycler_position);
            filterProductData("SPORTS", "sports_products", Constaints.product_id);
        }
        else if (Constaints.recycler_position==3){

            if (Constaints.mobile_category_position==1){
                Log.d("mobilebaifbaa",""+Constaints.category_position);
                categMobProductData(Constaints.product_id);
            }else if (Constaints.category_position==2){

                if (Constaints.category_product_position==0){
                    categoryProductData("FASHION","fashion_category","mens",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("FASHION","fashion_category","womens",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("FASHION", "fashion_category", "kids",Constaints.product_id);
                }
            }else if (Constaints.category_position==3){

                if (Constaints.category_product_position==0){
                    categoryProductData("ELECTRONICS","electronics_category","tv",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("ELECTRONICS","electronics_category","washingmachine",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("ELECTRONICS", "electronics_category", "fridge",Constaints.product_id);
                }else if (Constaints.category_product_position==3) {
                    categoryProductData("ELECTRONICS", "electronics_category", "computer_accessories",Constaints.product_id);
                }
            }else if (Constaints.category_position==4){

                if (Constaints.category_product_position==0){
                    categoryProductData("HOME APPLIANCES","home_category","home_hygiene",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("HOME APPLIANCES","home_category","kitchen",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("HOME APPLIANCES", "home_category", "decorelighting",Constaints.product_id);
                }
            }else if (Constaints.category_position==5){

                if (Constaints.category_product_position==0){
                    categoryProductData("GADGETS","gadgets_category","mobile_accessories",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("GADGETS","gadgets_category","smart_watch",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("GADGETS", "gadgets_category", "camera",Constaints.product_id);
                }else if (Constaints.category_product_position==3) {
                    categoryProductData("GADGETS", "gadgets_category", "styling devices",Constaints.product_id);
                }
            }else if (Constaints.category_position==6){

                if (Constaints.category_product_position==0){
                    categoryProductData("BEAUTY PRODUCTS","Beauty_category","skin_care",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("BEAUTY PRODUCTS","Beauty_category","makeup",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("BEAUTY PRODUCTS", "Beauty_category", "hair_care",Constaints.product_id);
                }else if (Constaints.category_product_position==3) {
                    categoryProductData("BEAUTY PRODUCTS", "Beauty_category", "fragnance_product",Constaints.product_id);
                }
            }else if (Constaints.category_position==7){

                if (Constaints.category_product_position==0){
                    categoryProductData("TOYS & GAMES","toys_games_category","for_boys",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("TOYS & GAMES","toys_games_category","for_girls",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("TOYS & GAMES", "toys_games_category", "for_infants",Constaints.product_id);
                }
            }else if (Constaints.category_position==8){

                if (Constaints.category_product_position==0){
                    categoryProductData("SPORTS","sports_category","sports",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("SPORTS","sports_category","fitness",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("SPORTS", "sports_category", "nutrition",Constaints.product_id);
                }
            }else if (Constaints.category_position==9){

                if (Constaints.category_product_position==0){
                    categoryProductData("FURNITURE","furniture_category","wardrobe",Constaints.product_id);
                }else if (Constaints.category_product_position==1){
                    categoryProductData("FURNITURE","furniture_category","bed_matresses",Constaints.product_id);
                }else if (Constaints.category_product_position==2) {
                    categoryProductData("FURNITURE", "furniture_category", "table_chairs",Constaints.product_id);
                }else if (Constaints.category_product_position==3) {
                    categoryProductData("FURNITURE", "furniture_category", "sofas",Constaints.product_id);
                }
            }
        }

        newindicator.setViewPager(newviewpage);

//        viewpageAdapter= new ItemShowViewPageAdap(getContext(),list);
//        newviewpage.setAdapter(viewpageAdapter);
//        newindicator.setViewPager(newviewpage);

        addToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constaints.current_user==null){

                }else {
                    if (ALREADY_ADDED_TO_WISHLIST) {
                        i="0";
                        ALREADY_ADDED_TO_WISHLIST = false;
                        addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.greyColor));
                        getProduct_AddToWishlist(i);
                    } else {
                        i="1";
                        ALREADY_ADDED_TO_WISHLIST=true;
                        addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.redColor));
                        getProduct_AddToWishlist(i);
                        addWishlistData(Constaints.product_id);
                    //    wishlist.add(Constaints.product_id);
                        Log.d("wishlistId",wishlist.get(0));
                        //Log.d("wishlistId",wishlist.get(1));
                        Toast.makeText(getContext(), "Added to wishlist Successfully", Toast.LENGTH_SHORT).show();

//                        Map<String, Object> addProduct = new HashMap<>();
//                        addProduct.put("Product_Id" + String.valueOf(wishlist.size()), Constaints.product_id);
//                        DocumentReference _ref = FirebaseFirestore.getInstance().collection("users").document(uid).;
//                            _ref.set(user_data).isSuccessful();
//                        firebaseFirestore.collection("users").document(Constaints.current_user).collection("My_Wishlist")
//                                .document().set(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Map<String, Object> updateListsize = new HashMap<>();
//                                    updateListsize.put("list_size", (long) (wishlist.size() + 1));
//                                    firebaseFirestore.collection("users").document(Constaints.current_user).collection("My_Wishlist")
//                                            .document().update(updateListsize).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                if (wishlistModelList.size()!=0){
//                                                    wishlistModelList.add(new MyWishlistModel(documentSnapshot.get("product_image_1").toString()
//                                                    ,documentSnapshot.get("product_title").toString()
//                                                    ,(long)documentSnapshot.get("free_coupons")
//                                                    ,documentSnapshot.get("rating").toString()
//                                                    ,(long)documentSnapshot.get("total_rating")
//                                                    ,documentSnapshot.get("product_price").toString()
//                                                    ,documentSnapshot.get("cutted_price").toString()
//                                                    ,documentSnapshot.get("COD").toString()));
//                                                }
//                                                ALREADY_ADDED_TO_WISHLIST = true;
//                                                addToWishlist.setImageTintList(ColorStateList.valueOf(Color.RED));
//                                                wishlist.add(Constaints.product_id);
//                                                Toast.makeText(getContext(), "Added to wishlist Successfully", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                String error = task.getException().getMessage();
//                                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    String error = task.getException().getMessage();
//                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                    }
                }
            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DeliveryActivity.class);
                startActivity(intent);
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //go to cart
                counter++;
                getProduct_AddToCart();
                Toast.makeText(getActivity(), "Added Item Successfully", Toast.LENGTH_SHORT).show();
                MyCartFragment myCartFragment = new MyCartFragment();
                fragmentManager = getParentFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, myCartFragment);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }

    void addWishlistData(String id){
        wishlist.add(id);
    }

    void colorData(){
        for (int i=0;i<wishlistModelList.size();i++){
            if (wishlistModelList.get(i).getColorCode().equals(1)){
                ALREADY_ADDED_TO_WISHLIST=true;
                addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.redColor));
            }else {
                ALREADY_ADDED_TO_WISHLIST=false;
                addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.greyColor));
            }
        }
    }
    /////////////////////homeFragment Data////////////////
    public void filterProductData(String p_doc, String p_collection,String id) {
        firebaseFirestore.collection("CATEGORIES").document(p_doc).collection(p_collection).document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();
                            mid= documentSnapshot.getString("id");
                            product_name.setText(documentSnapshot.getString("p_name"));

                            w_price = documentSnapshot.getString("p_price");
                            price.setText(documentSnapshot.getString("p_price"));

                            w_rating = documentSnapshot.getString("p_rating");

                            rating.setText(documentSnapshot.getString("p_rating"));

                            w_review = documentSnapshot.getString("p_review");
                            review.setText( documentSnapshot.getString("p_review"));

                                    documentSnapshot.getString("p_date");
                            description.setText(documentSnapshot.getString("p_desc"));

                            w_title= documentSnapshot.getString("p_title");

                            w_img = documentSnapshot.getString("img");
                                    ArrayList<SliderModel> sliderModels = (ArrayList<SliderModel>) documentSnapshot.get("p_img");
                            // sliderModelList.add(new SliderModel((String) documentSnapshot.get("b_image")));
                            for (int i = 0; i<sliderModels.size();i++)
                            {
                                sliderModelList.add(sliderModels.get(i));
                            }
                            Log.d("product_images", sliderModelList.toString());
                            sliderHomeAdapter.notifyDataSetChanged();

        //                    Log.d("list Images Of:", list.toString());
                            documentSnapshot.getString("p_brand");
                            for (int i=0;i<wishlist.size();i++){
                                if (wishlist.get(i)==Constaints.product_id){
                                    SharedPrefManager.getInstance(getActivity()).wishListItem(Constaints.product_id);
                                    ALREADY_ADDED_TO_WISHLIST=true;
                                    addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.redColor));
                                }else {
                                    ALREADY_ADDED_TO_WISHLIST=false;
                                    addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.greyColor));
                                }
                            }
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /////////////////////homeFragment Data////////////////

    ////////////////////////Category Mobile Data///////////////////////
    public void categMobProductData(String id) {
        firebaseFirestore.collection("CATEGORIES").document("MOBILES").collection("products").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();
                            mid= documentSnapshot.getString("id");
                            product_name.setText(documentSnapshot.getString("p_name"));

                            w_price = documentSnapshot.getString("p_price");
                            price.setText(documentSnapshot.getString("p_price"));

                            w_rating = documentSnapshot.getString("p_rating");

                            rating.setText(documentSnapshot.getString("p_rating"));

                            w_review = documentSnapshot.getString("p_review");
                            review.setText( documentSnapshot.getString("p_review"));

                            documentSnapshot.getString("p_date");
                            description.setText(documentSnapshot.getString("p_desc"));

                            w_title= documentSnapshot.getString("p_title");

                            w_img = documentSnapshot.getString("img");
                            ArrayList<SliderModel> sliderModels = (ArrayList<SliderModel>) documentSnapshot.get("p_img");
                            // sliderModelList.add(new SliderModel((String) documentSnapshot.get("b_image")));
                            for (int i = 0; i<sliderModels.size();i++)
                            {
                                sliderModelList.add(sliderModels.get(i));
                            }
                            Log.d("product_images", sliderModelList.toString());
                            sliderHomeAdapter.notifyDataSetChanged();

                           // Log.d("list Images Of:", list.toString());
                            documentSnapshot.getString("p_brand");
                            for (int i=0;i<wishlist.size();i++){
                                if (wishlist.get(i)==Constaints.product_id){
                                    SharedPrefManager.getInstance(getActivity()).wishListItem(Constaints.product_id);
                                    ALREADY_ADDED_TO_WISHLIST=true;
                                    addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.redColor));
                                }else {
                                    ALREADY_ADDED_TO_WISHLIST=false;
                                    addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.greyColor));
                                }
                            }
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    ////////////////////////CategoryMobile Data///////////////////////

    ///////////////////Category Other Product Data/////////////////////////////
    public void categoryProductData(String p_doc, String p_collection,String collection_type,String id) {
        firebaseFirestore.collection("CATEGORIES").document(p_doc).collection(p_collection).document(collection_type)
                .collection("products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            mid= documentSnapshot.getString("id");
                            product_name.setText(documentSnapshot.getString("p_name"));

                            w_price = documentSnapshot.getString("p_price");
                            price.setText(documentSnapshot.getString("p_price"));

                            w_rating = documentSnapshot.getString("p_rating");

                            rating.setText(documentSnapshot.getString("p_rating"));

                            w_review = documentSnapshot.getString("p_review");
                            review.setText( documentSnapshot.getString("p_review"));

                            documentSnapshot.getString("p_date");
                            description.setText(documentSnapshot.getString("p_desc"));

                            w_title= documentSnapshot.getString("p_title");

                            w_img = documentSnapshot.getString("img");
                            ArrayList<SliderModel> sliderModels = (ArrayList<SliderModel>) documentSnapshot.get("p_img");
                            // sliderModelList.add(new SliderModel((String) documentSnapshot.get("b_image")));
                            for (int i = 0; i<sliderModels.size();i++)
                            {
                                sliderModelList.add(sliderModels.get(i));
                            }
                            Log.d("product_images", sliderModelList.toString());
                            sliderHomeAdapter.notifyDataSetChanged();

                          //  Log.d("list Images Of:", list.toString());
                            documentSnapshot.getString("p_brand");
                            for (int i=0;i<wishlist.size();i++){
                                if (wishlist.get(i)==Constaints.product_id){
                                    SharedPrefManager.getInstance(getActivity()).wishListItem(Constaints.product_id);
                                    ALREADY_ADDED_TO_WISHLIST=true;
                                    addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.redColor));
                                }else {
                                    ALREADY_ADDED_TO_WISHLIST=false;
                                    addToWishlist.setImageTintList(ColorStateList.valueOf(Constaints.greyColor));
                                }
                            }
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    ///////////////////Category Other Product Data/////////////////////////////

    public void getProduct_AddToCart()
    {

        String count=String.valueOf(counter);
        HashMap<String, String> productData = new HashMap();
        productData.put("product_id", mid);
        productData.put("p_count", count);
        productData.put("product_title", w_title);
        productData.put("product_price", w_price);
        productData.put("product_image", w_img);
        productData.put("cutted_price", "9000");
        productData.put("coupons_applied", "false");
        productData.put("free_coupons", "2");
        productData.put("total_amount",""+price);
        DocumentReference _reference = FirebaseFirestore.getInstance().collection("whishlist")
                .document(Constaints.current_user).collection("my_cart").document(Constaints.product_id);
        _reference.set(productData).isSuccessful();
    }


    public void getProduct_AddToWishlist(String i)
    {
        HashMap<String, Object> productData = new HashMap();
        productData.put("product_id", mid);
        productData.put("product_title", w_title);
        productData.put("product_price", w_price);
        productData.put("product_image", w_img);
        productData.put("product_rating", w_rating);
        productData.put("total_rating", w_review);
        productData.put("cutted_price", "10000");
        productData.put("payment_method", "Cash On Delivery");
        productData.put("free_coupons", "2");
        productData.put("selected", i);
        DocumentReference _reference = FirebaseFirestore.getInstance().collection("whishlist").document(Constaints.current_user).collection("My_Wishlist").document(mid);
        _reference.set(productData).isSuccessful();
    }

    public void getWishlistData() {
        firebaseFirestore.collection("whishlist").document(Constaints.current_user).collection("My_Wishlist").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String l =  documentSnapshot.getString("free_coupons");

                                long coupans = Long.parseLong(l);

                                wishlistModelList.add(new MyWishlistModel(documentSnapshot.get("product_image").toString(),
                                        documentSnapshot.getString("product_title"),
                                        coupans,
                                        documentSnapshot.getString("product_rating"),
                                        documentSnapshot.getString("total_rating"),
                                        documentSnapshot.getString("product_price"),
                                        documentSnapshot.getString("cutted_price"),
                                        documentSnapshot.getString("payment_method"),
                                        documentSnapshot.getString("selected")));
                            }
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
