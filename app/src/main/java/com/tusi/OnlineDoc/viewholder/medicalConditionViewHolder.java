package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;
import com.tusi.OnlineDoc.DataLists.PatientMedicalHistoryList;
import com.tusi.OnlineDoc.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class medicalConditionViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "patientMedicalHistoryViewHolder";

    TextView illnessDescription;
    TextView illnessClinic;
    TextView illnessDate;
    TextView illnessDoctor;
    TextView illnessMedication;




    public medicalConditionViewHolder(View v) {
        super(v);
        illnessDescription = (TextView) itemView.findViewById(R.id.IllnessDescriptionResponse);
        illnessClinic = (TextView) itemView.findViewById(R.id.ClinicOfDiagnosisResponse);
        illnessDate = (TextView) itemView.findViewById(R.id.DateOfDiagnosisResponse);
        illnessDoctor = (TextView) itemView.findViewById(R.id.DoctorOfDiagnosisResponse);
        illnessMedication = (TextView) itemView.findViewById(R.id.MedicationOfDiagnosisResponse);

    }

    public void bindMessage(PatientMedicalHistoryList patientmedicalhistorylist) {
        if (patientmedicalhistorylist.getDescription() != null) {
            illnessDescription.setText(patientmedicalhistorylist.getDescription());
            illnessClinic.setText(patientmedicalhistorylist.getClinic());
            illnessDate.setText(patientmedicalhistorylist.getDate());
            illnessDoctor.setText(patientmedicalhistorylist.getDoctor());
            illnessMedication.setText(patientmedicalhistorylist.getMedication());

            illnessDescription.setVisibility(TextView.VISIBLE);
            illnessClinic.setVisibility(TextView.VISIBLE);
            illnessDate.setVisibility(TextView.VISIBLE);
            illnessDoctor.setVisibility(TextView.VISIBLE);
            illnessMedication.setVisibility(TextView.VISIBLE);

        }

    }
}