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


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tusi.OnlineDoc.DataLists.ClinicToBookList;
import com.tusi.OnlineDoc.MyScrollToBottomObserver;
import com.tusi.OnlineDoc.R;
import com.tusi.OnlineDoc.databinding.ClinicFollowedScreenBinding;
import com.tusi.OnlineDoc.viewholder.viewScreenViewHolder;
import com.tusi.OnlineDoc.viewholder.usertype;

import java.util.Calendar;


public class clinicfollowedscreenFragment extends Fragment {

    private static final String TAG = "ClinicFollowed";

    public static String MESSAGES_CHILD;

    public static final String ANONYMOUS = "anonymous";

    private GoogleSignInClient mSignInClient;
    private ClinicFollowedScreenBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private static TextView chosendate;
    private static TextView chosentime;
    private FirebaseRecyclerAdapter<ClinicToBookList, viewScreenViewHolder> mPatientToViewAdapter;
    private static String[] userType_ = {ANONYMOUS};
    static usertype utpe;
    String usr;


    public static clinicfollowedscreenFragment newInstance(int page, String title, usertype userType) {
        clinicfollowedscreenFragment fragment = new clinicfollowedscreenFragment();
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
        mBinding = ClinicFollowedScreenBinding.inflate(getLayoutInflater());
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAuth = FirebaseAuth.getInstance();
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

        Button Bookingbutton;
        Button datebutt;
        Button timebutt;
        Bookingbutton = (Button) view.findViewById(R.id.EditButtonBookAppointmentScreen);
        datebutt =(Button) view.findViewById(R.id.DateButton);
        timebutt =(Button) view.findViewById(R.id.TimeButton);

        chosendate = (TextView) view.findViewById(R.id.Chosendate);
        chosentime = (TextView) view.findViewById(R.id.ChosenTime);
        UserNameView = (TextView) view.findViewById(R.id.usernameBookAppointmentScreen);
        UserTypeView = (TextView) view.findViewById(R.id.usertypeBookAppointmentScreen);
        Intent intent = new Intent(Intent.ACTION_INSERT);

        final String[] bookdate = new String[1];
        final String[] time = new String[1];
        usr = getUserName();

        MESSAGES_CHILD = "Database/"+usr+"/Details/"+"clinicFollowed";


        mFirebaseAuth = FirebaseAuth.getInstance();


        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mBinding.messageRecyclerViewBookAppointmentScreen.setLayoutManager(mLinearLayoutManager);

        // Initialize Realtime Database
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = mDatabase.getReference().child(MESSAGES_CHILD);


        messagesRef.get().addOnCompleteListener(task -> {
            if (task.getResult().getValue()==null) {
                mBinding.progressBarBookAppointmentScreen.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(getActivity(), "No Medical Issues Registered yet",
                        Toast.LENGTH_SHORT).show();
                UserNameView.setText(getUserName());
                UserTypeView.setText(getUserType());

            }

        });
        FirebaseRecyclerOptions<ClinicToBookList> options =
                new FirebaseRecyclerOptions.Builder<ClinicToBookList>()
                        .setQuery(messagesRef, ClinicToBookList.class)
                        .build();

        mPatientToViewAdapter = new FirebaseRecyclerAdapter<ClinicToBookList, viewScreenViewHolder>(options) {
            @Override
            public viewScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new viewScreenViewHolder(inflater.inflate(R.layout.item_message_clinicdetails_patientview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(viewScreenViewHolder vh, int position, ClinicToBookList message) {
                mBinding.progressBarBookAppointmentScreen.setVisibility(ProgressBar.INVISIBLE);
                vh.bindMessage(message);
                Bookingbutton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {

                        if(true)
                        {Toast.makeText(getActivity(), vh.getEmail(), Toast.LENGTH_SHORT).show();}
                        else
                        {Toast.makeText(getActivity(), vh.getEmail(), Toast.LENGTH_SHORT).show();
                            intent.setData(CalendarContract.CONTENT_URI);
                            intent.putExtra(CalendarContract.Events.TITLE, "Booking Clinic Session");
                            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Booking Clinic Session");
                            intent.putExtra(CalendarContract.Events.DURATION, "PT1H");
                            intent.putExtra(CalendarContract.Events.ALL_DAY, false);
                            intent.putExtra(Intent.EXTRA_EMAIL,vh.getEmail());
                        }


                    }
                });
                UserNameView.setText(getUserName());
                UserTypeView.setText(getUserType());
            }

        };

        datebutt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });

        timebutt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.messageRecyclerViewBookAppointmentScreen.setLayoutManager(mLinearLayoutManager);
        mBinding.messageRecyclerViewBookAppointmentScreen.setAdapter(mPatientToViewAdapter);

        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver.java for details
        mPatientToViewAdapter.registerAdapterDataObserver(
                new MyScrollToBottomObserver(mBinding.messageRecyclerViewBookAppointmentScreen, mPatientToViewAdapter, mLinearLayoutManager));

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {


        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            chosentime.setText("Time: "+hourOfDay+":"+minute);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            chosendate.setText("Date: "+day+"/"+month+"/"+year);
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
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
        mPatientToViewAdapter.stopListening();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPatientToViewAdapter.startListening();

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
