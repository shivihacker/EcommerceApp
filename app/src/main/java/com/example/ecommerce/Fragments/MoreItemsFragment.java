package com.example.ecommerce.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Adapter.MoreItemAdapter;
import com.example.ecommerce.R;

public class MoreItemsFragment extends Fragment {

    RecyclerView newvertical_more;
    String[] str={"","","","","",""};
    Context ctx;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_more_item, container, false);
       newvertical_more = view.findViewById(R.id.newvertical_more);
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        newvertical_more.setLayoutManager(new LinearLayoutManager(getActivity()));
        newvertical_more.setHasFixedSize(true);
        MoreItemAdapter myAdapter=new MoreItemAdapter(str,getActivity());
        newvertical_more.setAdapter(myAdapter);

        return view;


    }
}
