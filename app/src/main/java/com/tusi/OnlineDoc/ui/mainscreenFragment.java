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


import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;


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
import com.tusi.OnlineDoc.DataLists.ClinicDetailsList;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.DataLists.PatientDetailList;
import com.tusi.OnlineDoc.MessageViewHolder;
import com.tusi.OnlineDoc.MyScrollToBottomObserver;
import com.tusi.OnlineDoc.R;
import com.tusi.OnlineDoc.databinding.MainScreenBinding;
import com.tusi.OnlineDoc.viewholder.*;
import com.tusi.OnlineDoc.viewholder.usertype;


public class mainscreenFragment extends Fragment {

    private static final String TAG = "MainScreenFragment";

    public static String MESSAGES_CHILD;

    public static final String ANONYMOUS = "anonymous";



    private MainScreenBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseRecyclerAdapter<PatientDetailList, mainScreenViewHolder> mPatientDetailsAdapter;
    private FirebaseRecyclerAdapter<ClinicDetailsList, mainClinicScreenViewHolder> mClinicDetailsAdapter;
    private static String[] userType_ = {ANONYMOUS};
    static usertype utpe;
    String usr;


    public static mainscreenFragment newInstance(int page, String title,usertype userType) {
        mainscreenFragment fragment = new mainscreenFragment();
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
        mBinding = MainScreenBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView UserTypeView;
        TextView UserNameView;
        UserNameView = (TextView) view.findViewById(R.id.username);
        UserTypeView = (TextView) view.findViewById(R.id.usertype);
        usr = getUserName();
        if (usr == "TUSI Solution")
        {
            Log.i("usr", usr);
        }
        else
        {
            Log.i("usr", "failed");}

//        mDatabase.getReference().child("Database").child("usr").child("TUSi Solution").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    //Todo: popup
//                }
//                else {
//                    utpe.setUser(String.valueOf(task.getResult().getValue()));
//                }
//            }
//        });

        if (utpe.getUser().contains("patient"))
        {
            MESSAGES_CHILD = "Database/"+usr;

        }
        else if (utpe.getUser().contains("clinic"))
        {
            MESSAGES_CHILD = "Database/"+usr;
        }
        else
        {
            MESSAGES_CHILD = "Database/"+usr;
        }

        mFirebaseAuth = FirebaseAuth.getInstance();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.messageRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Initialize Realtime Database
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = mDatabase.getReference().child(MESSAGES_CHILD);

        if (utpe.getUser().contains("patient"))
        {
            FirebaseRecyclerOptions<PatientDetailList> options =
                    new FirebaseRecyclerOptions.Builder<PatientDetailList>()
                            .setQuery(messagesRef, PatientDetailList.class)
                            .build();

            mPatientDetailsAdapter = new FirebaseRecyclerAdapter<PatientDetailList, mainScreenViewHolder>(options) {
                @Override
                public mainScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    return new mainScreenViewHolder(inflater.inflate(R.layout.item_message_patientdetails, viewGroup, false));
                }

                @Override
                protected void onBindViewHolder(mainScreenViewHolder vh, int position, PatientDetailList message) {
                    mBinding.progressBar.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };

            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerView.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerView.setAdapter(mPatientDetailsAdapter);

            // Scroll down when a new message arrives
            // See MyScrollToBottomObserver.java for details
            mPatientDetailsAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerView, mPatientDetailsAdapter, mLinearLayoutManager));

        }
        else if (utpe.getUser().contains("clinic"))
        {
            FirebaseRecyclerOptions<ClinicDetailsList> options =
                    new FirebaseRecyclerOptions.Builder<ClinicDetailsList>()
                            .setQuery(messagesRef, ClinicDetailsList.class)
                            .build();


            mClinicDetailsAdapter = new FirebaseRecyclerAdapter<ClinicDetailsList, mainClinicScreenViewHolder>(options) {
                @Override
                public mainClinicScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    return new mainClinicScreenViewHolder(inflater.inflate(R.layout.item_message_clinicdetails, viewGroup, false));
                }

                @Override
                protected void onBindViewHolder(mainClinicScreenViewHolder vh, int position, ClinicDetailsList message) {
                    mBinding.progressBar.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };
            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerView.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerView.setAdapter(mClinicDetailsAdapter);

            // Scroll down when a new message arrives
            // See MyScrollToBottomObserver.java for details
            mClinicDetailsAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerView, mClinicDetailsAdapter, mLinearLayoutManager));
        }
        else
        {
            FirebaseRecyclerOptions<PatientDetailList> options =
                    new FirebaseRecyclerOptions.Builder<PatientDetailList>()
                            .setQuery(messagesRef, PatientDetailList.class)
                            .build();

            mPatientDetailsAdapter = new FirebaseRecyclerAdapter<PatientDetailList, mainScreenViewHolder>(options) {
                @Override
                public mainScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    return new mainScreenViewHolder(inflater.inflate(R.layout.item_message_patientdetails, viewGroup, false));
                }

                @Override
                protected void onBindViewHolder(mainScreenViewHolder vh, int position, PatientDetailList message) {
                    mBinding.progressBar.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };
            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerView.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerView.setAdapter(mPatientDetailsAdapter);

            // Scroll down when a new message arrives
            // See MyScrollToBottomObserver.java for details
            mPatientDetailsAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerView, mPatientDetailsAdapter, mLinearLayoutManager));
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
//
//        if (requestCode == REQUEST_IMAGE) {
//            if (resultCode == RESULT_OK && data != null) {
//                final Uri uri = data.getData();
//                Log.d(TAG, "Uri: " + uri.toString());
//
//                final FirebaseUser user = mFirebaseAuth.getCurrentUser();
//                FriendlyMessage tempMessage = new FriendlyMessage(
//                        null, getUserName(), getUserPhotoUrl(), LOADING_IMAGE_URL);
//
//                mDatabase.getReference().child(MESSAGES_CHILD).setValue(tempMessage, new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(DatabaseError databaseError,
//                                                   DatabaseReference databaseReference) {
//                                if (databaseError != null) {
//                                    Log.w(TAG, "Unable to write message to database.",
//                                            databaseError.toException());
//                                    return;
//                                }
//
//                                // Build a StorageReference and then upload the file
//                                String key = databaseReference.getKey();
//                                StorageReference storageReference =
//                                        FirebaseStorage.getInstance()
//                                                .getReference(user.getUid())
//                                                .child(key)
//                                                .child(uri.getLastPathSegment());
//
//                                putImageInStorage(storageReference, uri, key);
//                            }
//                        });
//            }
//        }
//    }

//    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
//        // First upload the image to Cloud Storage
//        storageReference.putFile(uri)
//                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // After the image loads, get a public downloadUrl for the image
//                        // and Fadd it to the message.
//                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
//                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        FriendlyMessage friendlyMessage = new FriendlyMessage(
//                                                null, getUserName(), getUserPhotoUrl(), uri.toString());
//                                        mDatabase.getReference()
//                                                .child(MESSAGES_CHILD)
//                                                .child(key)
//                                                .setValue(friendlyMessage);
//                                    }
//                                });
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Image upload task was not successful.", e);
//                    }
//                });
//    }

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
            mPatientDetailsAdapter.stopListening();

        }
        else if (utpe.getUser().contains("clinic"))
        {
            mClinicDetailsAdapter.stopListening();
        }
        else
        {
            mPatientDetailsAdapter.stopListening();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (utpe.getUser().contains("patient"))
        {
            mPatientDetailsAdapter.startListening();

        }
        else if (utpe.getUser().contains("clinic"))
        {
            mClinicDetailsAdapter.startListening();
        }
        else
        {
            mPatientDetailsAdapter.startListening();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

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
