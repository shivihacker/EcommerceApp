package com.example.ecommerce.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.Adapter.MyOrderAdapter;
import com.example.ecommerce.Model.MyOrderItemModel;
import com.example.ecommerce.R;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment {
    RecyclerView myOrderRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order, container, false);

        myOrderRecyclerView=view.findViewById(R.id.my_order_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.realme,"Real Me Pro (32 GB)","Delivered on Feb 06,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.realme,"Real Me Pro (32 GB)","Delivered on Feb 06,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.realme,"Real Me Pro (32 GB)","Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.realme,"Real Me Pro (32 GB)","Delivered on Feb 06,2020"));

        MyOrderAdapter myOrderAdapter=new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
        return view;
    }
}