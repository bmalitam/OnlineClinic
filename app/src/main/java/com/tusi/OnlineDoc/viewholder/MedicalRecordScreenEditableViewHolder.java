package com.tusi.OnlineDoc.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tusi.OnlineDoc.DataLists.PatientMedicalHistoryEditList;
import com.tusi.OnlineDoc.R;

public class MedicalRecordScreenEditableViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "PatientMedicalHistoryList";

    TextView IllnessDescription;
    TextView ClinicOfDiagnosis;
    TextView DateOfDiagnosis;
    TextView DoctorOfDiagnosis;
    TextView MedicationOfDiagnosis;

    public MedicalRecordScreenEditableViewHolder(View v) {
        super(v);
        IllnessDescription = (TextView) itemView.findViewById(R.id.pIllnessDescriptionResponse);
        ClinicOfDiagnosis = (TextView) itemView.findViewById(R.id.pClinicOfDiagnosisResponse);
        DateOfDiagnosis = (TextView) itemView.findViewById(R.id.pDateOfDiagnosisResponse);
        DoctorOfDiagnosis = (TextView) itemView.findViewById(R.id.pDoctorOfDiagnosisResponse);
        MedicationOfDiagnosis = (TextView) itemView.findViewById(R.id.pMedicationOfDiagnosisResponse);

    }

    public void bindMessage(PatientMedicalHistoryEditList patientmedicaledithistoryList) {
        if (patientmedicaledithistoryList.getClinic() != null) {
            ClinicOfDiagnosis.setText(patientmedicaledithistoryList.getClinic());
            DateOfDiagnosis.setText(patientmedicaledithistoryList.getDate());
            IllnessDescription.setText(patientmedicaledithistoryList.getDescription());
            DoctorOfDiagnosis.setText(patientmedicaledithistoryList.getDoctor());
            MedicationOfDiagnosis.setText(patientmedicaledithistoryList.getMedication());


            ClinicOfDiagnosis.setVisibility(TextView.VISIBLE);
            DateOfDiagnosis.setVisibility(TextView.VISIBLE);
            IllnessDescription.setVisibility(TextView.VISIBLE);
            DoctorOfDiagnosis.setVisibility(TextView.VISIBLE);
            MedicationOfDiagnosis.setVisibility(TextView.VISIBLE);
        }

    }
}