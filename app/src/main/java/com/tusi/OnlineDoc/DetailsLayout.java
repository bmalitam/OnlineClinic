package com.tusi.OnlineDoc;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tusi.OnlineDoc.databinding.DetailsLayoutBinding;

public class DetailsLayout extends AppCompatActivity {
    private DetailsLayoutBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DetailsLayoutBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // Set click listeners
        mBinding.SendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });


    }
}
