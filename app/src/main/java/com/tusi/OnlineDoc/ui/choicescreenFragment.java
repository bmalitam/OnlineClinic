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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tusi.OnlineDoc.ApplicationVariable;
import com.tusi.OnlineDoc.DataLists.PatientFollowingList;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;

import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.DataLists.PatientFollowingList;
import com.tusi.OnlineDoc.MessageViewHolder;
import com.tusi.OnlineDoc.MyScrollToBottomObserver;
import com.tusi.OnlineDoc.R;
import com.tusi.OnlineDoc.databinding.ChoiceScreenBinding;

import com.tusi.OnlineDoc.viewholder.*;


public class choicescreenFragment extends Fragment {

    private static final String TAG = "MainActivity";

    public static String MESSAGES_CHILD = "Database/Patient/Patient_1/Clinic_Followed";

    public static final String ANONYMOUS = "anonymous";


    private ChoiceScreenBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseRecyclerAdapter<PatientFollowingList, choiceClinicScreenViewHolder> mClinicToViewAdapter;
    private FirebaseRecyclerAdapter<ClinicFollowedList, choiceScreenViewHolder> mPatientToViewAdapter;

    public static choicescreenFragment newInstance(int page, String title) {
        choicescreenFragment fragment = new choicescreenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("someInt", page);
        bundle.putString("someTitle", title);
        fragment.setArguments(bundle);
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

        // This codelab uses View Binding
        // See: https://developer.android.com/topic/libraries/view-binding

        TextView UserTypeView;
        TextView UserNameView;
        UserNameView = (TextView) view.findViewById(R.id.usernameChoiceScreen);
        UserTypeView = (TextView) view.findViewById(R.id.usertypeChoiceScreen);

        if (getUserType() =="Patient User")
        {
            MESSAGES_CHILD = "Database/Database_Clinic/Clinic";

        }
        else if (getUserType() =="Clinic User")
        {
            MESSAGES_CHILD = "Database/Database_Patient/Patient";
        }
        else
        {
             MESSAGES_CHILD = "Database/Database_Clinic/Clinic";
        }

        mFirebaseAuth = FirebaseAuth.getInstance();


        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mBinding.messageRecyclerViewChoiceScreen.setLayoutManager(mLinearLayoutManager);

        // Initialize Realtime Database
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = mDatabase.getReference().child(MESSAGES_CHILD);


        if (getUserType() =="Patient User")
        {
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
                protected void onBindViewHolder(choiceScreenViewHolder vh, int position, ClinicFollowedList message) {
                    mBinding.progressBarChoiceScreen.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };

            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerViewChoiceScreen.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerViewChoiceScreen.setAdapter(mPatientToViewAdapter);

            // Scroll down when a new message arrives
            // See MyScrollToBottomObserver.java for details
            mPatientToViewAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerViewChoiceScreen, mPatientToViewAdapter, mLinearLayoutManager));

        }
        else if (getUserType() =="Clinic User")
        {
            FirebaseRecyclerOptions<PatientFollowingList> options =
                    new FirebaseRecyclerOptions.Builder<PatientFollowingList>()
                            .setQuery(messagesRef, PatientFollowingList.class)
                            .build();


            mClinicToViewAdapter = new FirebaseRecyclerAdapter<PatientFollowingList, choiceClinicScreenViewHolder>(options) {
                @Override
                public choiceClinicScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    return new choiceClinicScreenViewHolder(inflater.inflate(R.layout.item_message_patientdetails_clinicview, viewGroup, false));
                }

                @Override
                protected void onBindViewHolder(choiceClinicScreenViewHolder vh, int position, PatientFollowingList message) {
                    mBinding.progressBarChoiceScreen.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };
            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerViewChoiceScreen.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerViewChoiceScreen.setAdapter(mClinicToViewAdapter);

            // Scroll down when a new message arrives
            // See MyScrollToBottomObserver.java for details
            mClinicToViewAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerViewChoiceScreen, mClinicToViewAdapter, mLinearLayoutManager));
        }
        else
        {
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
                protected void onBindViewHolder(choiceScreenViewHolder vh, int position, ClinicFollowedList message) {
                    mBinding.progressBarChoiceScreen.setVisibility(ProgressBar.INVISIBLE);
                    vh.bindMessage(message);
                    UserNameView.setText(getUserName());
                    UserTypeView.setText(getUserType());
                }
            };
            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mLinearLayoutManager.setStackFromEnd(true);
            mBinding.messageRecyclerViewChoiceScreen.setLayoutManager(mLinearLayoutManager);
            mBinding.messageRecyclerViewChoiceScreen.setAdapter(mPatientToViewAdapter);

            // Scroll down when a new message arrives
            // See MyScrollToBottomObserver.java for details
            mPatientToViewAdapter.registerAdapterDataObserver(
                    new MyScrollToBottomObserver(mBinding.messageRecyclerViewChoiceScreen, mPatientToViewAdapter, mLinearLayoutManager));
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
//
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
        if (getUserType() =="Patient User")
        {
            mPatientToViewAdapter.stopListening();

        }
        else if (getUserType() =="Clinic User")
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
        if (getUserType() =="Patient User")
        {
            mPatientToViewAdapter.startListening();

        }
        else if (getUserType() =="Clinic User")
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
        String usertypevariable = ((ApplicationVariable) this.getActivity().getApplication()).getUserTypeVariable();
        if (usertypevariable != null) {
            return usertypevariable;
        }

        return ANONYMOUS;
    }

}
