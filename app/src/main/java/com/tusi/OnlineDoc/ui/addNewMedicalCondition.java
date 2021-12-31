/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tusi.OnlineDoc.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.DataLists.PatientMedicalHistoryList;
import com.tusi.OnlineDoc.MessageViewHolder;
import com.tusi.OnlineDoc.MyScrollToBottomObserver;
import com.tusi.OnlineDoc.R;
import com.tusi.OnlineDoc.SignInActivity;
import com.tusi.OnlineDoc.databinding.EditableMedichistScreenBinding;
import com.tusi.OnlineDoc.databinding.ViewScreenBinding;
import com.tusi.OnlineDoc.viewholder.medicalConditionViewHolder;
import com.tusi.OnlineDoc.viewholder.usertype;
import com.tusi.OnlineDoc.DataLists.PatientMedicalHistoryEditList;
import com.tusi.OnlineDoc.viewholder.MedicalRecordScreenEditableViewHolder;
import com.tusi.OnlineDoc.ui.username;

public class addNewMedicalCondition extends AppCompatActivity {

    private static final String TAG = "addNewMedicalCondition";
    public static final String ANONYMOUS = "anonymous";
    private LinearLayoutManager mLinearLayoutManager;
    public static String MESSAGES_CHILD;
    private GoogleSignInClient mSignInClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    EditableMedichistScreenBinding mBinding;
    private FirebaseRecyclerAdapter<PatientMedicalHistoryEditList, MedicalRecordScreenEditableViewHolder> mPatientToViewAdapter;
    static username usern;
    String usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = EditableMedichistScreenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        TextView UserTypeView;
        UserTypeView = (TextView) findViewById(R.id.usertypemededit);
//        usern = new username(this);
//        usr = usern.getUserName();
        MESSAGES_CHILD = "Database/"+"suha rama"+"/Details/"+"medicalHistory";

        mLinearLayoutManager = new LinearLayoutManager(addNewMedicalCondition.this);
        mLinearLayoutManager.setStackFromEnd(true);

        mBinding.messageRecyclerViewmededit.setLayoutManager(mLinearLayoutManager);

        // Initialize Realtime Database
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = mDatabase.getReference().child(MESSAGES_CHILD);

        messagesRef.get().addOnCompleteListener(task -> {
            if (task.getResult().getValue()==null) {
                mBinding.progressBarmededit.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(addNewMedicalCondition.this, "No Medical Issues Registered yet",
                        Toast.LENGTH_SHORT).show();
                UserTypeView.setText("Clinic");

            }

        });
        FirebaseRecyclerOptions<PatientMedicalHistoryEditList> options =
                new FirebaseRecyclerOptions.Builder<PatientMedicalHistoryEditList>()
                        .setQuery(messagesRef, PatientMedicalHistoryEditList.class)
                        .build();

        mPatientToViewAdapter = new FirebaseRecyclerAdapter<PatientMedicalHistoryEditList, MedicalRecordScreenEditableViewHolder>(options) {
            @Override
            public MedicalRecordScreenEditableViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new MedicalRecordScreenEditableViewHolder(inflater.inflate(R.layout.patientmedicalhistory, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(MedicalRecordScreenEditableViewHolder vh, int position, PatientMedicalHistoryEditList message) {
                mBinding.progressBarmededit.setVisibility(ProgressBar.INVISIBLE);
                vh.bindMessage(message);
                UserTypeView.setText("Clinic");
            }
        };

        mLinearLayoutManager = new LinearLayoutManager(addNewMedicalCondition.this);
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.messageRecyclerViewmededit.setLayoutManager(mLinearLayoutManager);
        mBinding.messageRecyclerViewmededit.setAdapter(mPatientToViewAdapter);


        mPatientToViewAdapter.registerAdapterDataObserver(
                new MyScrollToBottomObserver(mBinding.messageRecyclerViewmededit, mPatientToViewAdapter, mLinearLayoutManager));

        mPatientToViewAdapter.startListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        mFirebaseAuth.signOut();
        mSignInClient.signOut();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }

        return ANONYMOUS;
    }


}
