package com.tusi.OnlineDoc.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class choiceScreenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = "clinicListForPatientViewHolder";

    TextView clinicNameFollowed;
    TextView clinicContactFollowed;
    TextView clinicRegistrationNumberFollowed;
    TextView clinicLocationFollowed;
    String emailtobook;
    String emailtobookprev;
    String nametobook;
    String nametobookprev;
    String regtobook;
    String regtobookprev;
    String loctobook;
    String loctobookprev;
    int pos;
    int posprev;

    public choiceScreenViewHolder(View v) {
        super(v);
        clinicNameFollowed = (TextView) itemView.findViewById(R.id.NameClinicDetailPatientView);
        clinicContactFollowed = (TextView) itemView.findViewById(R.id.EmailClinicDetailPatientView);
        clinicRegistrationNumberFollowed = (TextView) itemView.findViewById(R.id.RegNumClinicDetailPatientView);
        clinicLocationFollowed = (TextView) itemView.findViewById(R.id.LocationClinicDetailPatientView);

        clinicNameFollowed.setOnClickListener(this);
        itemView.setOnClickListener(this);
        clinicContactFollowed.setOnClickListener(this);
        clinicRegistrationNumberFollowed.setOnClickListener(this);
        clinicLocationFollowed.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {


        emailtobook = clinicContactFollowed.getText().toString();
        nametobook = clinicNameFollowed.getText().toString();
        regtobook = clinicRegistrationNumberFollowed.getText().toString();
        loctobook = clinicLocationFollowed.getText().toString();
        pos = getLayoutPosition();

        if (emailtobook!=emailtobookprev)
        { itemView.setBackgroundResource(R.color.mydefault);
            emailtobookprev = emailtobook;
            nametobookprev = nametobook;
            regtobookprev = regtobook;
            loctobookprev = loctobook;
            posprev = pos;
        }
        else
        {
            itemView.setBackgroundResource(R.color.white);
            emailtobookprev = "";
            nametobookprev = "";
            regtobookprev = "";
            loctobookprev = "";
            posprev = (Integer) null;

        }

    }
    public String getEmail()
    {
        return emailtobookprev;
    }
    public String getName()
    {
        return nametobookprev;
    }
    public String getReg()
    {
        return regtobookprev;
    }
    public String getLoc()
    {
        return loctobookprev;
    }
    public int getPos(){return  posprev;}
}