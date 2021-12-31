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


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tusi.OnlineDoc.ApplicationVariable;
import com.tusi.OnlineDoc.ChooseUserTypeActivity;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.DataLists.PatientFollowingList;
import com.tusi.OnlineDoc.DataLists.PatientMedicalHistoryList;
import com.tusi.OnlineDoc.MyScrollToBottomObserver;
import com.tusi.OnlineDoc.R;
import com.tusi.OnlineDoc.SignInActivity;
import com.tusi.OnlineDoc.databinding.ViewScreenBinding;
import com.tusi.OnlineDoc.viewholder.*;
import com.tusi.OnlineDoc.viewholder.usertype;
import com.tusi.OnlineDoc.ui.username;


public class viewscreenFragment extends Fragment {

    private static final String TAG = "MainActivity";

    public static String MESSAGES_CHILD;

    public static final String ANONYMOUS = "anonymous";

    String emailtobook;
    String emailtobookprev;
    String regnum;
    String location;
    String names;
    boolean followflag = false;
    private ViewScreenBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseRecyclerAdapter<PatientFollowingList, viewClinicScreenViewHolder> mClinicToViewAdapter;
    private FirebaseRecyclerAdapter<PatientMedicalHistoryList, medicalConditionViewHolder> mPatientToViewAdapter;
    private static String[] userType_ = {ANONYMOUS};
    static usertype utpe;
    String usr;


    public static viewscreenFragment newInstance(int page, String title,usertype userType) {
        viewscreenFragment fragment = new viewscreenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("someInt", page);
        bundle.putString("someTitle", title);
        fragment.setArguments(bundle);
        utpe = userType;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = ViewScreenBinding.inflate(getLayoutInflater());
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        View view = mBinding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TextView UserTypeView;
        TextView UserNameView;
        UserNameView = (TextView) view.findViewById(R.id.usernameViewScreen);
        UserTypeView = (TextView) view.findViewById(R.id.usertypeViewScreen);
        usr = getUserName();

        if (utpe.getUser().contains("patient"))
        {
            MESSAGES_CHILD = "Database/"+usr+"/Details/"+"medicalHistory";

        }
        else if (utpe.getUser().contains("clinic"))
        {
            MESSAGES_CHILD = "Database/"+usr+ "/Details"+"/patientUnder";
        }
        else
        {
            MESSAGES_CHILD = "Database/"+usr+"/Details/"+"medicalHistory";
        }

        mFirebaseAuth = FirebaseAuth.getInstance();


        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mBinding.messageRecyclerViewViewScreen.setLayoutManager(mLinearLayoutManager);

        // Initialize Realtime Database
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = mDatabase.getReference().child(MESSAGES_CHILD);


        if (utpe.getUser().contains("patient"))
        {
            messagesRef.get().addOnCompleteListener(task -> {
                if (task.getResult().getValue()==null) {
                    mBinding.progressBarViewScreen.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(getActivity(), "No Medical Issues Registered yet",
                            Toast.LENGTH_SHORT).show();
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());

                }

            });
            FirebaseRecyclerOptions<PatientMedicalHistoryList> options =
                    new FirebaseRecyclerOptions.Builder<PatientMedicalHistoryList>()
                            .setQuery(messagesRef, PatientMedicalHistoryList.class)
                            .build();

            mPatientToViewAdapter = new FirebaseRecyclerAdapter<PatientMedicalHistoryList, medicalConditionViewHolder>(options) {
                @Override
                public medicalConditionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    return new medicalConditionViewHolder(inflater.inflate(R.layout.item_message_patientmedicalhistory, viewGroup, false));
                }

                @Override
                protected void onBindViewHolder(medicalConditionViewHolder vh, int position, PatientMedicalHistoryList message) {
                    mBinding.progressBarViewScreen.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };

            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerViewViewScreen.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerViewViewScreen.setAdapter(mPatientToViewAdapter);


            mPatientToViewAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerViewViewScreen, mPatientToViewAdapter, mLinearLayoutManager));

        }
        else if (utpe.getUser().contains("clinic"))
        {
            messagesRef.get().addOnCompleteListener(task -> {
                if (task.getResult().getValue()==null) {
                    mBinding.progressBarViewScreen.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(getActivity(), "No patient Following Clinic yet",
                            Toast.LENGTH_SHORT).show();
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());

                }

            });
            FirebaseRecyclerOptions<PatientFollowingList> options =
                    new FirebaseRecyclerOptions.Builder<PatientFollowingList>()
                            .setQuery(messagesRef, PatientFollowingList.class)
                            .build();


            mClinicToViewAdapter = new FirebaseRecyclerAdapter<PatientFollowingList, viewClinicScreenViewHolder>(options) {
                @Override
                public viewClinicScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    return new viewClinicScreenViewHolder(inflater.inflate(R.layout.item_message_patientdetails_clinicview, viewGroup, false));
                }

                @Override
                protected void onBindViewHolder(viewClinicScreenViewHolder vh, @SuppressLint("RecyclerView") int position, PatientFollowingList message) {
                    mBinding.progressBarViewScreen.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickaction(vh,mClinicToViewAdapter,position);

                        }
                    });
                    vh.patientName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickaction(vh,mClinicToViewAdapter,position);
                        }
                    });
                    vh.patientContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickaction(vh,mClinicToViewAdapter,position);
                        }
                    });

                }
            };
            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerViewViewScreen.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerViewViewScreen.setAdapter(mClinicToViewAdapter);


            mClinicToViewAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerViewViewScreen, mClinicToViewAdapter, mLinearLayoutManager));
        }
        else
        {
            messagesRef.get().addOnCompleteListener(task -> {
                if (task.getResult().getValue()==null) {
                    mBinding.progressBarViewScreen.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(getActivity(), "No Medical Issues Registered yet",
                            Toast.LENGTH_SHORT).show();
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());

                }

            });
            FirebaseRecyclerOptions<PatientMedicalHistoryList> options =
                    new FirebaseRecyclerOptions.Builder<PatientMedicalHistoryList>()
                            .setQuery(messagesRef, PatientMedicalHistoryList.class)
                            .build();

            mPatientToViewAdapter = new FirebaseRecyclerAdapter<PatientMedicalHistoryList, medicalConditionViewHolder>(options) {
                @Override
                public medicalConditionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    return new medicalConditionViewHolder(inflater.inflate(R.layout.item_message_patientmedicalhistory, viewGroup, false));
                }

                @Override
                protected void onBindViewHolder(medicalConditionViewHolder vh, int position, PatientMedicalHistoryList message) {
                    mBinding.progressBarViewScreen.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };

            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerViewViewScreen.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerViewViewScreen.setAdapter(mPatientToViewAdapter);

            // Scroll down when a new message arrives
            // See MyScrollToBottomObserver.java for details
            mPatientToViewAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerViewViewScreen, mPatientToViewAdapter, mLinearLayoutManager));
        }

    }

    void clickaction(viewClinicScreenViewHolder vh, FirebaseRecyclerAdapter<PatientFollowingList, viewClinicScreenViewHolder> mClinicToViewAdapter, int position)
    {
        emailtobook = mClinicToViewAdapter.getItem(position).getName();

//        if (emailtobook!=emailtobookprev)
//        {   followflag = true;
//            vh.itemView.setBackgroundResource(R.color.mydefault);
//            emailtobookprev = emailtobook;
//            String id= mClinicToViewAdapter.getItem(position).getName();
//            Log.w("choiceScreenFragment", "ID "+id);
//
//        }
//        else
//        {
//            vh.itemView.setBackgroundResource(R.color.white);
//            emailtobookprev = "";
//            followflag = false;
//
//        }
        startActivity(new Intent(getContext(), addNewMedicalCondition.class));
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {
        if (utpe.getUser().contains("patient"))
        {
            mPatientToViewAdapter.stopListening();

        }
        else if (utpe.getUser().contains("clinic"))
        {
            mClinicToViewAdapter.stopListening();
        }
        else
        {
            mPatientToViewAdapter.stopListening();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (utpe.getUser().contains("patient"))
        {
            mPatientToViewAdapter.startListening();

        }
        else if (utpe.getUser().contains("clinic"))
        {
            mClinicToViewAdapter.startListening();
        }
        else
        {
            mPatientToViewAdapter.startListening();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sign_out_menu:
//                signOut();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private void signOut() {
//        mFirebaseAuth.signOut();
//        mSignInClient.signOut();
//        startActivity(new Intent(this, SignInActivity.class));
//        finish();
//    }

    @Nullable
    private String getUserPhotoUrl() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }

        return null;
    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }

        return ANONYMOUS;
    }

    private String getUserType() {
        if (utpe.getUser().contains("patient"))
        {
            return "Patient User";
        }
        else if (utpe.getUser().contains("clinic"))
        {
            return "Clinic User";
        }
        else
        {
            return ANONYMOUS;
        }

    }

}
