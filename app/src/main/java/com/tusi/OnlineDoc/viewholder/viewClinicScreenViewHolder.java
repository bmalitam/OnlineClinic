package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.PatientDetailList;
import com.tusi.OnlineDoc.DataLists.PatientFollowingList;
import com.tusi.OnlineDoc.R;


public class viewClinicScreenViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "patientDetailsViewHolder";

    TextView patientName;
    TextView patientContact;

    public viewClinicScreenViewHolder(View v) {
        super(v);
        patientName = (TextView) itemView.findViewById(R.id.NamePatientDetailClinicView);
        patientContact = (TextView) itemView.findViewById(R.id.ContactPatientDetailClinicView);

    }

    public void bindMessage(PatientFollowingList patientfollowinglist) {
        if (patientfollowinglist.getName() != null) {
            patientName.setText(patientfollowinglist.getName());
            patientContact.setText(patientfollowinglist.getContact());


            patientName.setVisibility(TextView.VISIBLE);
            patientContact.setVisibility(TextView.VISIBLE);



        }

    }
}