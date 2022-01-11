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

public class ClinicToBookList {

    private String Email;
    private String Location;
    private String Name;
    private String RegistrationNumber;


    public ClinicToBookList() {
    }

    public ClinicToBookList(String email, String location, String name, String registrationnumber) {
        this.Email = email;
        this.Location = location;
        this.Name = name;
        this.RegistrationNumber = registrationnumber;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getLocation() {
        return Location;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setRegistrationNumber(String registrationnumber) { this.RegistrationNumber = registrationnumber; }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }


}
