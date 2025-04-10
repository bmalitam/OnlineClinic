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
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tusi.OnlineDoc.ApplicationVariable;
import com.tusi.OnlineDoc.DataLists.ClinicToBookList;
import com.tusi.OnlineDoc.DataLists.PatientFollowingList;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;

import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.DataLists.PatientFollowingList;
import com.tusi.OnlineDoc.MessageViewHolder;
import com.tusi.OnlineDoc.MyScrollToBottomObserver;
import com.tusi.OnlineDoc.R;
import com.tusi.OnlineDoc.SignInActivity;
import com.tusi.OnlineDoc.databinding.ChoiceScreenBinding;
import com.tusi.OnlineDoc.viewholder.usertype;

import com.tusi.OnlineDoc.viewholder.*;

import java.util.Random;


public class choicescreenFragment extends Fragment {

    private static final String TAG = "MainActivity";

    public static String MESSAGES_CHILD;

    public static final String ANONYMOUS = "anonymous";

    String emailtobook;
    String emailtobookprev;
    String regnum;
    String location;
    String names;
    boolean followflag = false;


    private ChoiceScreenBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseRecyclerAdapter<PatientFollowingList, choiceClinicScreenViewHolder> mClinicToViewAdapter;
    private FirebaseRecyclerAdapter<ClinicFollowedList, choiceScreenViewHolder> mPatientToViewAdapter;
    private static String[] userType_ = {ANONYMOUS};
    static usertype utpe;
    String name;
    MutableLiveData<String> usrtyp = new MutableLiveData<>();
    String usr;

    public static choicescreenFragment newInstance(int page, String title, usertype userType) {
        choicescreenFragment fragment = new choicescreenFragment();
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
        mBinding = ChoiceScreenBinding.inflate(getLayoutInflater());


        View view = mBinding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView UserTypeView;
        TextView UserNameView;
        UserNameView = (TextView) view.findViewById(R.id.usernameChoiceScreen);
        UserTypeView = (TextView) view.findViewById(R.id.usertypeChoiceScreen);
        Button Follow = (Button) view.findViewById(R.id.EditButtonChoiceScreen);
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        final choiceScreenViewHolder[] vhold = new choiceScreenViewHolder[1];


        MESSAGES_CHILD = "Database/Database_Clinic/Clinic";


        mFirebaseAuth = FirebaseAuth.getInstance();


        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mBinding.messageRecyclerViewChoiceScreen.setLayoutManager(mLinearLayoutManager);

        // Initialize Realtime Database
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = mDatabase.getReference().child(MESSAGES_CHILD);
        usr = getUserName();

        FirebaseRecyclerOptions<ClinicFollowedList> options =
                new FirebaseRecyclerOptions.Builder<ClinicFollowedList>()
                        .setQuery(messagesRef, ClinicFollowedList.class)
                        .build();

        mPatientToViewAdapter = new FirebaseRecyclerAdapter<ClinicFollowedList, choiceScreenViewHolder>(options) {
            @Override
            public choiceScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new choiceScreenViewHolder(inflater.inflate(R.layout.item_message_clinicdetails_patientview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final choiceScreenViewHolder vh, @SuppressLint("RecyclerView") int position, ClinicFollowedList message) {
                mBinding.progressBarChoiceScreen.setVisibility(ProgressBar.INVISIBLE);
                vh.bindMessage(message);
                name=mPatientToViewAdapter.getItem(vh.getLayoutPosition()).getName();

                UserNameView.setText(getUserName());
                UserTypeView.setText(getUserType());
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickaction(vh,mPatientToViewAdapter,position);

                    }



                });
                vh.clinicNameFollowed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickaction(vh,mPatientToViewAdapter,position);
                    }



                });
                vh.clinicContactFollowed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickaction(vh,mPatientToViewAdapter,position);
                    }



                });
                vh.clinicRegistrationNumberFollowed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickaction(vh,mPatientToViewAdapter,position);
                    }



                });
                vh.clinicLocationFollowed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickaction(vh,mPatientToViewAdapter,position);
                    }



                });



            }

        };

            Follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (followflag){Toast.makeText(getActivity(), "Followed: " + names, Toast.LENGTH_SHORT).show();
                        ClinicFollowedList list = new ClinicFollowedList();
                        PatientFollowingList plist = new PatientFollowingList();
                        //push information in XML to dataclass (CLINIC)
                        list.setName(names);
                        list.setEmail(emailtobook);
                        list.setLocation(location);
                        list.setRegistrationNumber(regnum);

                        plist.setName(usr);
                        plist.setContact(mFirebaseAuth.getCurrentUser().getEmail());


                        final int min = 20;
                        final int max = 80;
                        final int random = new Random().nextInt((max - min) + 1) + min;

                        mDatabase.getReference().child("Database").child(usr).child("Details").child("clinicFollowed").child("Clinic_" + random).setValue(list);
                        mDatabase.getReference().child("Database").child(names).child("Details").child("patientUnder").child("Patient_" + random).setValue(plist);
                    }
                    else{Toast.makeText(getActivity(), "Choose a clinic first", Toast.LENGTH_SHORT).show();}


                }
            });

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.messageRecyclerViewChoiceScreen.setLayoutManager(mLinearLayoutManager);
        mBinding.messageRecyclerViewChoiceScreen.setAdapter(mPatientToViewAdapter);



        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver.java for details
        mPatientToViewAdapter.registerAdapterDataObserver(
                new MyScrollToBottomObserver(mBinding.messageRecyclerViewChoiceScreen, mPatientToViewAdapter, mLinearLayoutManager));


    }

    void clickaction(choiceScreenViewHolder vh, FirebaseRecyclerAdapter<ClinicFollowedList, choiceScreenViewHolder> mPatientToViewAdapter, int position)
    {
        emailtobook = mPatientToViewAdapter.getItem(position).getEmail();
        regnum = mPatientToViewAdapter.getItem(position).getRegistrationNumber();
        names = mPatientToViewAdapter.getItem(position).getName();
        location = mPatientToViewAdapter.getItem(position).getLocation();

        if (emailtobook!=emailtobookprev)
        {   followflag = true;
            vh.itemView.setBackgroundResource(R.color.mydefault);
            emailtobookprev = emailtobook;
            String id= mPatientToViewAdapter.getItem(position).getEmail();
            Log.w("choiceScreenFragment", "ID "+id);

        }
        else
        {
            vh.itemView.setBackgroundResource(R.color.white);
            emailtobookprev = "";
            followflag = false;

        }
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
