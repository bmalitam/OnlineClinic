/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tusi.OnlineDoc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.tusi.OnlineDoc.DataLists.UserTypeInformation;
import com.tusi.OnlineDoc.DataLists.UserTypeInformationClinic;
import com.tusi.OnlineDoc.DataLists.UserTypeData;
import com.tusi.OnlineDoc.DataLists.ClinicDetailsList;
import com.tusi.OnlineDoc.DataLists.PatientMedicalHistoryList;
import com.tusi.OnlineDoc.DataLists.PatientDetailList;

import com.tusi.OnlineDoc.databinding.DetailsLayoutClinicBinding;
import android.widget.Toast;

import java.util.Random;


public class AddDetailsActivity extends AppCompatActivity {
    private static final String TAG = "addDetailsActivity";
    private DetailsLayoutClinicBinding mBindingclinic;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBindingclinic = DetailsLayoutClinicBinding.inflate(getLayoutInflater());
        setContentView(mBindingclinic.getRoot());
        mDatabase = FirebaseDatabase.getInstance();

        //define variables
        mFirebaseAuth = FirebaseAuth.getInstance();
        Switch usertypeswitch = (Switch) mBindingclinic.UserType;
        EditText name = (EditText)  mBindingclinic.nameadddets;
        EditText location = (EditText)  mBindingclinic.locationdets;
        EditText phonenumber = (EditText)  mBindingclinic.contactnumberdets;
        EditText patientstatus = (EditText)  mBindingclinic.statdets;
        EditText registrationumber = (EditText)  mBindingclinic.regnumdets;

        //get inforamtion from XML

        Editable nametext = name.getText();
        Editable locationtext = location.getText();
        Editable phonenumtext = phonenumber.getText();
        Editable patientstatustext = patientstatus.getText();
        Editable regnumtext = registrationumber.getText();


        //Instantiate data classes
        UserTypeInformation userdataclass = new UserTypeInformation();
        UserTypeData usertypecalss = new UserTypeData();
        UserTypeInformationClinic userdataclinicclass = new UserTypeInformationClinic();
        ClinicDetailsList clinicdeets = new ClinicDetailsList();



        String usr=mFirebaseAuth.getCurrentUser().getDisplayName();
        mBindingclinic.SendDetails.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


                boolean usrtype = usertypeswitch.isChecked();

                if(usrtype == false)
                {usertypecalss.setUsertype("patient");}
                else
                {usertypecalss.setUsertype("clinic");}

                mDatabase.getReference().child("Database").child("usr").child(usr).setValue(usertypecalss);
                if (usrtype == false){
                    //push information in XML to dataclass (PATIENT)


                    userdataclass.setName(mFirebaseAuth.getCurrentUser().getDisplayName());
                    userdataclass.setContact(phonenumtext.toString());
                    userdataclass.setStatus(patientstatustext.toString());
                    userdataclass.setClinicFollowed("");
                    userdataclass.setMedicalHistory("");

                    mDatabase.getReference().child("Database").child(usr).child("Details").setValue(userdataclass);
                }
                else{

                    //push information in XML to dataclass (CLINIC)
                    userdataclinicclass.setName(nametext.toString());
                    userdataclinicclass.setEmail(mFirebaseAuth.getCurrentUser().getEmail());
                    userdataclinicclass.setLocation(locationtext.toString());
                    userdataclinicclass.setRegistrationNumber(regnumtext.toString());
                    userdataclinicclass.setPatientUnder("");
                    clinicdeets.setName(nametext.toString());
                    clinicdeets.setEmail(mFirebaseAuth.getCurrentUser().getEmail());
                    clinicdeets.setLocation(locationtext.toString());
                    clinicdeets.setRegistrationNumber(regnumtext.toString());

                    final int min = 20;
                    final int max = 80;
                    final int random = new Random().nextInt((max - min) + 1) + min;
                    mDatabase.getReference().child("Database").child(usr).child("Details").setValue(userdataclinicclass);
                    mDatabase.getReference().child("Database").child("Database_Clinic").child("Clinic").child("Clinic_Registered_"+random).setValue(clinicdeets);
                }

                Log.w(TAG, "Added Details");
                startActivity(new Intent(AddDetailsActivity.this, MainActivity.class));
                finish();
            }
        });
    }



}

