package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ecommerce.Adapter.AddressAdapter;
import com.example.ecommerce.Model.AddressModal;
import com.example.ecommerce.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerce.Activities.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressAct extends AppCompatActivity {
    private RecyclerView myaddressRecyclerview;
    private Button deliverBtn;
    private List<AddressModal> addressModalList;
    private static AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        myaddressRecyclerview=findViewById(R.id.recycler_no_of_address);
        deliverBtn=findViewById(R.id.deliver_here);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Address");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myaddressRecyclerview.setLayoutManager(linearLayoutManager);
        addressModalList=new ArrayList<>();
        addressModalList.add(new AddressModal("Shivani Shruti","jafgakshfdaslk","123466",true));
        addressModalList.add(new AddressModal("Shivani cfhjgk","jafgakshfdaslk","123466",false));
        addressModalList.add(new AddressModal("Shivani srivastav","jafgakshfdaslk","123466",false));

        int mode=getIntent().getIntExtra("MODE",-1);
        if (mode==SELECT_ADDRESS){
            deliverBtn.setVisibility(View.VISIBLE);
        }else {
            deliverBtn.setVisibility(View.GONE);
        }
        addressAdapter=new AddressAdapter(addressModalList,mode);
        myaddressRecyclerview.setAdapter(addressAdapter);
        ((SimpleItemAnimator)myaddressRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();

    }
    public static void refreshItem(int deselect,int select){
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}