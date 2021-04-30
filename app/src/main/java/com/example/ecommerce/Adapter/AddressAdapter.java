package com.example.ecommerce.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Model.AddressModal;
import com.example.ecommerce.R;

import java.util.List;

import static com.example.ecommerce.Activities.DeliveryActivity.SELECT_ADDRESS;
import static com.example.ecommerce.Activities.MyAddressAct.refreshItem;
import static com.example.ecommerce.Activities.ProfilePage.MANAGE_ADDRESS;

public class AddressAdapter  extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

     List<AddressModal> addressModalList;
     private int MODE;
     private int preSelectedPosition=-1;

    public AddressAdapter(List<AddressModal> addressModalList, int MODE) {
        this.addressModalList = addressModalList;
        this.MODE = MODE;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        String name=addressModalList.get(position).getFullname();
        String address=addressModalList.get(position).getAddress();
        String pincode=addressModalList.get(position).getPincode();
        Boolean selected=addressModalList.get(position).getSelected();
        holder.setData(name,address,pincode,selected,position);
    }

    @Override
    public int getItemCount() {
        return addressModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname,address,pincode;
        private ImageView icon;
        private LinearLayout optionContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname=itemView.findViewById(R.id.fullname_layout);
            address=itemView.findViewById(R.id.address_layout);
            pincode=itemView.findViewById(R.id.pincode_layout);
            icon=itemView.findViewById(R.id.icon_address);
            optionContainer=itemView.findViewById(R.id.option_container);
        }
        private void setData(String username, String userAddress, String userPincode, Boolean selected, final int position){
            fullname.setText(username);
            address.setText(userAddress);
            pincode.setText(userPincode);

            if (MODE==SELECT_ADDRESS){
                icon.setImageResource(R.drawable.check);
                if (selected){
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition=position;
                }else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelectedPosition!=position){
                            addressModalList.get(position).setSelected(true);
                            addressModalList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition,position);
                            preSelectedPosition=position;
                        }
                    }
                });
            }else if (MODE==MANAGE_ADDRESS){
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.vertical_dots);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=-1;
                    }
                });
            }
        }
    }
}
