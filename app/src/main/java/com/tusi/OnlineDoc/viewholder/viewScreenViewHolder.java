package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.ClinicDetailsList;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.R;

public class viewScreenViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "clinicDetailsViewHolder";

    TextView clinicName;
    TextView clinicContact;
    TextView clinicRegistrationNumber;
    TextView clinicLocation;


    public viewScreenViewHolder(View v) {
        super(v);
        clinicName = (TextView) itemView.findViewById(R.id.NameClinicDetailPatientView);
        clinicContact = (TextView) itemView.findViewById(R.id.EmailClinicDetailPatientView);
        clinicRegistrationNumber = (TextView) itemView.findViewById(R.id.RegNumClinicDetailPatientView);
        clinicLocation = (TextView) itemView.findViewById(R.id.LocationClinicDetailPatientView);

    }

    public void bindMessage(ClinicFollowedList clinicfollowedlist) {
        if (clinicfollowedlist.getName() != null) {
            clinicName.setText(clinicfollowedlist.getName());
            clinicContact.setText(clinicfollowedlist.getEmail());
            clinicRegistrationNumber.setText(clinicfollowedlist.getRegistrationNumber());
            clinicLocation.setText(clinicfollowedlist.getLocation());

            clinicName.setVisibility(TextView.VISIBLE);
            clinicContact.setVisibility(TextView.VISIBLE);
            clinicRegistrationNumber.setVisibility(TextView.VISIBLE);
            clinicLocation.setVisibility(TextView.VISIBLE);

        }

    }
}