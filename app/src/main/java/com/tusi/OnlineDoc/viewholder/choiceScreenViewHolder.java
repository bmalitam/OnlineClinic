package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class choiceScreenViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "clinicListForPatientViewHolder";

    TextView clinicNameFollowed;
    TextView clinicContactFollowed;
    TextView clinicRegistrationNumberFollowed;
    TextView clinicLocationFollowed;


    public choiceScreenViewHolder(View v) {
        super(v);
        clinicNameFollowed = (TextView) itemView.findViewById(R.id.NameClinicDetailPatientView);
        clinicContactFollowed = (TextView) itemView.findViewById(R.id.EmailClinicDetailPatientView);
        clinicRegistrationNumberFollowed = (TextView) itemView.findViewById(R.id.RegNumClinicDetailPatientView);
        clinicLocationFollowed = (TextView) itemView.findViewById(R.id.LocationClinicDetailPatientView);


    }

    public void bindMessage(ClinicFollowedList clinicfollowedlist) {
        if (clinicfollowedlist.getName() != null) {
            clinicNameFollowed.setText(clinicfollowedlist.getName());
            clinicContactFollowed.setText(clinicfollowedlist.getEmail());
            clinicRegistrationNumberFollowed.setText(clinicfollowedlist.getRegistrationNumber());
            clinicLocationFollowed.setText(clinicfollowedlist.getLocation());

            clinicNameFollowed.setVisibility(TextView.VISIBLE);
            clinicContactFollowed.setVisibility(TextView.VISIBLE);
            clinicRegistrationNumberFollowed.setVisibility(TextView.VISIBLE);
            clinicLocationFollowed.setVisibility(TextView.VISIBLE);

        }
    }
}