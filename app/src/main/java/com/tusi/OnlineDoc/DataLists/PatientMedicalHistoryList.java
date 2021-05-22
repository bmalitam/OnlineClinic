/**
 * Copyright TUSi Enterprise All Rights Reserved.
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
package com.tusi.OnlineDoc.DataLists;

public class PatientMedicalHistoryList {

    private String Clinic;
    private String Date;
    private String Description;
    private String Doctor;
    private String Medication;



    public PatientMedicalHistoryList() {
    }

    public PatientMedicalHistoryList(String clinic, String date, String description, String doctor, String medication) {
        this.Clinic = clinic;
        this.Date = date;
        this.Description = description;
        this.Doctor = doctor;
        this.Medication = medication;

    }

    public void setClinic(String clinic) {
        this.Clinic = clinic;
    }

    public String getClinic() { return Clinic; }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        this.Doctor = doctor;
    }

    public String getMedication() {
        return Medication;
    }

    public void setMedication(String medication) {
        this.Medication = medication;
    }

}
