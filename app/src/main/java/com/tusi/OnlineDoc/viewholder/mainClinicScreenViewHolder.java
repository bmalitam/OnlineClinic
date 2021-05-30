package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.ClinicDetailsList;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainClinicScreenViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "clinicDetailsViewHolder";

    TextView clinicName;
    TextView clinicContact;
    TextView clinicRegistrationNumber;
    TextView clinicLocation;


    public mainClinicScreenViewHolder(View v) {
        super(v);
        clinicName = (TextView) itemView.findViewById(R.id.NameClinicDetail);
        clinicContact = (TextView) itemView.findViewById(R.id.EmailClinicDetail);
        clinicRegistrationNumber = (TextView) itemView.findViewById(R.id.ClinicRegistrationNumberResponse);
        clinicLocation = (TextView) itemView.findViewById(R.id.ClinicLocationResponse);

    }

    public void bindMessage(ClinicDetailsList clinicdetailslist) {
        if (clinicdetailslist.getName() != null) {
            clinicName.setText(clinicdetailslist.getName());
            clinicContact.setText(clinicdetailslist.getEmail());
            clinicRegistrationNumber.setText(clinicdetailslist.getRegistrationNumber());
            clinicLocation.setText(clinicdetailslist.getLocation());

            clinicName.setVisibility(TextView.VISIBLE);
            clinicContact.setVisibility(TextView.VISIBLE);
            clinicRegistrationNumber.setVisibility(TextView.VISIBLE);
            clinicLocation.setVisibility(TextView.VISIBLE);

        }

    }
}