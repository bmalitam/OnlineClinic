package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.DataLists.PatientFollowingList;
import com.tusi.OnlineDoc.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class choiceClinicScreenViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "patientListForClinicViewHolder";

    TextView patientNameFollowed;
    TextView patientContactFollowed;


    public choiceClinicScreenViewHolder(View v) {
        super(v);
        patientNameFollowed = (TextView) itemView.findViewById(R.id.NamePatientDetailClinicView);
        patientContactFollowed = (TextView) itemView.findViewById(R.id.ContactPatientDetailClinicView);

    }

    public void bindMessage(PatientFollowingList patientfollowinglist) {
        if (patientfollowinglist.getName() != null) {
            patientNameFollowed.setText(patientfollowinglist.getName());
            patientContactFollowed.setText(patientfollowinglist.getContact());


            patientNameFollowed.setVisibility(TextView.VISIBLE);
            patientContactFollowed.setVisibility(TextView.VISIBLE);
        }


    }
}