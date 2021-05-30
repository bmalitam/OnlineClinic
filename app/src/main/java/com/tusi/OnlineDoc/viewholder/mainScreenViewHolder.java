package com.tusi.OnlineDoc.viewholder;

import android.view.View;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.tusi.OnlineDoc.DataLists.PatientDetailList;
import com.tusi.OnlineDoc.R;



public class mainScreenViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "patientDetailsViewHolder";

    TextView patientName;
    TextView patientContact;
    TextView patientStatus;
    public mainScreenViewHolder(View v) {
        super(v);
        patientName = (TextView) itemView.findViewById(R.id.NamePatientDetail);
        patientContact = (TextView) itemView.findViewById(R.id.ContactPatientDetail);
        patientStatus = (TextView) itemView.findViewById(R.id.PatientStatusResponse);
    }

    public void bindMessage(PatientDetailList patientdetailList) {
        if (patientdetailList.getName() != null) {
            patientName.setText(patientdetailList.getName());
            patientContact.setText(patientdetailList.getContact());
            patientStatus.setText(patientdetailList.getStatus());

            patientName.setVisibility(TextView.VISIBLE);
            patientContact.setVisibility(TextView.VISIBLE);
            patientStatus.setVisibility(TextView.VISIBLE);


        }

    }
}