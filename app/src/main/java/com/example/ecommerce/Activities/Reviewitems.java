package com.example.ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.skydoves.elasticviews.ElasticButton;

public class Reviewitems extends AppCompatActivity {

    TextView more_review;
    ElasticButton review_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewitems);
        review_back=findViewById(R.id.review_back);
        more_review=findViewById(R.id.more_review);

        more_review.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),Viewallreview.class);
                        startActivity(intent);
                    }
                }
        );
        review_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}
