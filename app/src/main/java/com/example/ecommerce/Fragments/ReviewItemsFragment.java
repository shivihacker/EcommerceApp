package com.example.ecommerce.Fragments;

import android.content.Intent;import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ecommerce.Activities.Viewallreview;
import com.example.ecommerce.R;

public class ReviewItemsFragment extends Fragment {
    TextView more_review,ratingTextView,totalreview;
    private RatingBar ratingStar;

    public ReviewItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_reviewitems, container, false);
        // Inflate the layout for this fragment
        more_review=view.findViewById(R.id.more_review);
        totalreview=view.findViewById(R.id.reviewitem_totalreview);
        ratingStar=view.findViewById(R.id.reviewitem_ratingbar);
        ratingTextView=view.findViewById(R.id.reviewitem_ratingtextview);

        ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingTextView.setText(""+rating);
            }
        });
        more_review.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), Viewallreview.class);
                        startActivity(intent);
                    }
                }
        );

        return view; }

}
