package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.PatientMedicalHistoryList;
import com.tusi.OnlineDoc.R;

public class MedicalRecordScreenViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "PatientMedicalHistoryList";

    TextView IllnessDescription;
    TextView ClinicOfDiagnosis;
    TextView DateOfDiagnosis;
    TextView DoctorOfDiagnosis;
    TextView MedicationOfDiagnosis;

    public MedicalRecordScreenViewHolder(View v) {
        super(v);
        IllnessDescription = (TextView) itemView.findViewById(R.id.IllnessDescriptionResponse);
        ClinicOfDiagnosis = (TextView) itemView.findViewById(R.id.ClinicOfDiagnosisResponse);
        DateOfDiagnosis = (TextView) itemView.findViewById(R.id.DateOfDiagnosisResponse);
        DoctorOfDiagnosis = (TextView) itemView.findViewById(R.id.DoctorOfDiagnosisResponse);
        MedicationOfDiagnosis = (TextView) itemView.findViewById(R.id.MedicationOfDiagnosisResponse);

    }

    public void bindMessage(PatientMedicalHistoryList patientmedicalhistoryList) {
        if (patientmedicalhistoryList.getClinic() != null) {
            ClinicOfDiagnosis.setText(patientmedicalhistoryList.getClinic());
            DateOfDiagnosis.setText(patientmedicalhistoryList.getDate());
            IllnessDescription.setText(patientmedicalhistoryList.getDescription());
            DoctorOfDiagnosis.setText(patientmedicalhistoryList.getDoctor());
            MedicationOfDiagnosis.setText(patientmedicalhistoryList.getMedication());


            ClinicOfDiagnosis.setVisibility(TextView.VISIBLE);
            DateOfDiagnosis.setVisibility(TextView.VISIBLE);
            IllnessDescription.setVisibility(TextView.VISIBLE);
            DoctorOfDiagnosis.setVisibility(TextView.VISIBLE);
            MedicationOfDiagnosis.setVisibility(TextView.VISIBLE);
        }

    }
}