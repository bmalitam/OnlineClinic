package com.tusi.OnlineDoc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.tusi.OnlineDoc.databinding.DetailsLayoutBinding;
import com.tusi.OnlineDoc.databinding.DetailsLayoutClinicBinding;
import com.tusi.OnlineDoc.databinding.ChooseUserTypeBinding;
public class ChooseUserTypeActivity  extends AppCompatActivity {
    private static final String TAG = "addUserTypeActivity";
    private ChooseUserTypeBinding mBinding;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ChooseUserTypeBinding.inflate(getLayoutInflater());
        mFirebaseAuth  = FirebaseAuth.getInstance();
        setContentView(mBinding.getRoot());
        // Set click listeners
        checkDatabaseifUserExists(mFirebaseAuth);

    }



    private void checkDatabaseifUserExists(FirebaseAuth fauth) {
        Log.d(TAG, "Current User:" + fauth.getCurrentUser().getDisplayName());

        mDatabase = FirebaseDatabase.getInstance();
        String username =fauth.getCurrentUser().getDisplayName();
        mDatabase.getReference().child("Database").child(username).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(getApplicationContext(),"User Does Not Exists",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChooseUserTypeActivity.this, AddDetailsActivity.class));
                finish();
            }
            else {
                if(task.getResult().getValue()!=null) {
                    Toast.makeText(getApplicationContext(), "User Exists", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChooseUserTypeActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"User Does Not Exists",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChooseUserTypeActivity.this, AddDetailsActivity.class));
                    finish();
                }
            }
        });
    }


}
