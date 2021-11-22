package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.ClinicToBookList;
import com.tusi.OnlineDoc.R;

import java.util.ArrayList;
import java.util.List;

public class viewScreenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = "clinicDetailsViewHolder";

    TextView clinicName;
    TextView clinicContact;
    TextView clinicRegistrationNumber;
    TextView clinicLocation;
    List<View> itemViewList = new ArrayList<>();
    String emailtobook;
    String emailtobookprev;




    public viewScreenViewHolder(View v) {
        super(v);
        clinicName = (TextView) itemView.findViewById(R.id.NameClinicDetailPatientView);
        clinicContact = (TextView) itemView.findViewById(R.id.EmailClinicDetailPatientView);
        clinicRegistrationNumber = (TextView) itemView.findViewById(R.id.RegNumClinicDetailPatientView);
        clinicLocation = (TextView) itemView.findViewById(R.id.LocationClinicDetailPatientView);
        clinicName.setOnClickListener(this);
        itemView.setOnClickListener(this);
        clinicContact.setOnClickListener(this);
        clinicRegistrationNumber.setOnClickListener(this);
        clinicLocation.setOnClickListener(this);
        itemViewList.add(itemView);

    }

    public void bindMessage(ClinicToBookList clinicfollowedlist) {
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


    @Override
    public void onClick(View view) {

        emailtobook = clinicContact.getText().toString();

        if (emailtobook!=emailtobookprev)
        { itemView.setBackgroundResource(R.color.mydefault);
            emailtobookprev = emailtobook;
            }
        else
        {
            itemView.setBackgroundResource(R.color.white);
            emailtobookprev = "";

        }



    }

        public String getEmail()
        {
            return emailtobookprev;
        }
}