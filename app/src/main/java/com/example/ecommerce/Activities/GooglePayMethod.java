package com.example.ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.Helper.Constaints;
import com.example.ecommerce.Helper.SharedPrefManager;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GooglePayMethod extends AppCompatActivity implements PaymentResultListener {

    EditText amount, note, email, mobileNo;
    Button send;
    double amt;
    String TAG ="main";
    final int UPI_PAYMENT = 0;
    String uname,value,totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_pay_method);
        Checkout.preload(getApplicationContext());
        FirebaseFirestore.getInstance().collection("users").document(Constaints.current_user).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            Log.d("Not Exist", "List is empty");
                            return;
                        } else {
                            try {
                                 uname = documentSnapshot.get("user_name").toString();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Gender field none", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
        value = sharedPrefManager.getTotalAmount();
        amt=Double.parseDouble(value);
        totalAmount=String.valueOf(amt);

        send = (Button) findViewById(R.id.send);
        amount = (EditText)findViewById(R.id.amount_et);
        note = (EditText)findViewById(R.id.note);
        email = (EditText) findViewById(R.id.name);
        mobileNo =(EditText) findViewById(R.id.upi_id);
        amount.setText(totalAmount);
        email.setText("Shivani Training");
        mobileNo.setText("8083153751");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting the values from the EditTexts
                if (TextUtils.isEmpty(email.getText().toString().trim())){
                    Toast.makeText(GooglePayMethod.this," Name is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(mobileNo.getText().toString().trim())){
                    Toast.makeText(GooglePayMethod.this," UPI ID is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(note.getText().toString().trim())){
                    Toast.makeText(GooglePayMethod.this," Note is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(amount.getText().toString().trim())){
                    Toast.makeText(GooglePayMethod.this," Amount is invalid", Toast.LENGTH_SHORT).show();
                }else{
                    startPayment();
//                    payUsingUpi(name.getText().toString(), upivirtualid.getText().toString(),
//                            note.getText().toString(), amount.getText().toString());
                }
            }
        });
    }

    public void startPayment(){
        /**
         * * Instantiate Checout
         */
        Checkout checkout=new Checkout();
        checkout.setKeyID("rzp_test_Kjnvy1TVKFNZu7");

        /**
         * SEt your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Referwnce to current activity
         */
        final Activity activity=this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options =new JSONObject();
            options.put("name","Shivani Training");
            options.put("description","Demo Charge");
            options.put("send_sms_hash",true);
            options.put("allow_rotation",true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp=mobile/images/r");
            options.put("currency","INR");
            options.put("amount","100");

            JSONObject preFill=new JSONObject();
            preFill.put("email","shivanishrutis@gmail.com");
            preFill.put("contact","8083153751");
            options.put("prefill",preFill);
            checkout.open(activity,options);
        }catch (Exception e){
            Log.e(TAG,"Error in starting Razorpay Checkout",e);
        }
    }

    @Override
    public void onPaymentSuccess(String s){
        Toast.makeText(getApplicationContext(), "Payment Successfull", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i,String s){
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }

//    void payUsingUpi(  String name,String upiId, String note, String amount) {
//        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
//        Uri uri = Uri.parse("upi://pay").buildUpon()
//                .appendQueryParameter("pa", upiId)
//                .appendQueryParameter("pn", name)
//                .appendQueryParameter("mc", "5732")
//                //.appendQueryParameter("tid", "02125412")
//                //.appendQueryParameter("tr", "25584584")
//                .appendQueryParameter("tn", note)
//                .appendQueryParameter("am", amount)
//                .appendQueryParameter("cu", "INR")
//                //.appendQueryParameter("refUrl", "blueapp")
//                .build();
//        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
//        upiPayIntent.setData(uri);
//        // will always show a dialog to user to choose an app
//        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
//        // check if intent resolves
//        if(null != chooser.resolveActivity(getPackageManager())) {
//            startActivityForResult(chooser, UPI_PAYMENT);
//        } else {
//            Toast.makeText(GooglePayMethod.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("main ", "response "+resultCode );
//        /*
//       E/main: response -1
//       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
//       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
//       E/UPI: payment successfull: 922118921612
//         */
//        switch (requestCode) {
//            case UPI_PAYMENT:
//                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
//                    if (data != null) {
//                        String trxt = data.getStringExtra("response");
//                        Log.e("UPI", "onActivityResult: " + trxt);
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add(trxt);
//                        upiPaymentDataOperation(dataList);
//                    } else {
//                        Log.e("UPI", "onActivityResult: " + "Return data is null");
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add("nothing");
//                        upiPaymentDataOperation(dataList);
//                    }
//                } else {
//                    //when user simply back without payment
//                    Log.e("UPI", "onActivityResult: " + "Return data is null");
//                    ArrayList<String> dataList = new ArrayList<>();
//                    dataList.add("nothing");
//                    upiPaymentDataOperation(dataList);
//                }
//                break;
//        }
//    }
//
//    private void upiPaymentDataOperation(ArrayList<String> data) {
//        if (isConnectionAvailable(GooglePayMethod.this)) {
//            String str = data.get(0);
//            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
//            String paymentCancel = "";
//            if(str == null) str = "discard";
//            String status = "";
//            String approvalRefNo = "";
//            String response[] = str.split("&");
//            for (int i = 0; i < response.length; i++) {
//                String equalStr[] = response[i].split("=");
//                if(equalStr.length >= 2) {
//                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
//                        status = equalStr[1].toLowerCase();
//                    }
//                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
//                        approvalRefNo = equalStr[1];
//                    }
//                }
//                else {
//                    paymentCancel = "Payment cancelled by user.";
//                }
//            }
//            if (status.equals("success")) {
//                //Code to handle successful transaction here.
//                Toast.makeText(GooglePayMethod.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
//
//                //////////firebase//////////////////
//                HashMap<String, String> productData = new HashMap();
//                productData.put("user_id",Constaints.current_user );
//                productData.put("order_id",FirebaseFirestore.getInstance().collection("whislist")
//                        .document(Constaints.current_user).collection("payment_details").getId());
//                productData.put("user_name",uname);
//                productData.put("approval_ref_no",approvalRefNo);
//                productData.put("status",status);
//                productData.put("amount",value);
//                DocumentReference _reference = FirebaseFirestore.getInstance().collection("whislist")
//                        .document(Constaints.current_user).collection("payment_details").document();
//                _reference.set(productData).isSuccessful();
//                ///////////////////////////////////////////////////////
//                Log.d("orderId",FirebaseFirestore.getInstance().collection("whislist")
//                        .document(Constaints.current_user).collection("payment_details").getId());
//
//                Log.e("UPI", "payment successfull: "+approvalRefNo);
//            }
//            else if("Payment cancelled by user.".equals(paymentCancel)) {
//                Toast.makeText(GooglePayMethod.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
//
//            }
//            else {
//                Toast.makeText(GooglePayMethod.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "failed payment: "+approvalRefNo);
//
//            }
//        } else {
//            Log.e("UPI", "Internet issue: ");
//
//            Toast.makeText(GooglePayMethod.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public static boolean isConnectionAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();////////////////////////////
//            if (netInfo != null && netInfo.isConnected()
//                    && netInfo.isConnectedOrConnecting()
//                    && netInfo.isAvailable()) {
//                return true;
//            }
//        }
//        return false;
//    }
}