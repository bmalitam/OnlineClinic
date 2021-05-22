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
package com.tusi.OnlineDoc.DataLists;

public class PatientFollowingList {

    private String Name;
    private String Contact;

    public PatientFollowingList() {
    }

    public PatientFollowingList(String name, String contact) {
        this.Name = name;
        this.Contact = contact;
    }

    public void setContact(String text) {
        this.Contact = text;
    }

    public String getContact() { return Contact; }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

}
