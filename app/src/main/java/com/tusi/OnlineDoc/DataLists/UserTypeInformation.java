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



import java.util.Map;

public class UserTypeInformation {

    private String Name;
    private String Contact;
    private String Status;
    private String MedicalHistory;
    private String ClinicFollowed;

    public UserTypeInformation() {
    }

    public UserTypeInformation( String name, String contact, String status, String medical_history, String clinic_followed) {
        this.Name = name;
        this.Contact = contact;
        this.Status = status;
        this.MedicalHistory = medical_history;
        this.ClinicFollowed = clinic_followed;
    }


    public void setName(String name) {
        this.Name = name;
    }
    public String getName() {
        return Name;
    }

    public void setContact(String contact) {
        this.Contact = contact;
    }
    public String getContact() {
        return Contact;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
    public String getStatus() {
        return Status;
    }

    public void setMedicalHistory(String medical_history) {
        this.MedicalHistory = medical_history;
    }
    public String getMedicalHistory() {

        return MedicalHistory;
    }

    public void setClinicFollowed(String clinic_followed) {
        this.ClinicFollowed = clinic_followed;
    }
    public String getClinicFollowed() {



        return ClinicFollowed;
    }


}
