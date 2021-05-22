package com.tusi.OnlineDoc;

import android.widget.Switch;
import android.view.View;
import com.tusi.OnlineDoc.databinding.ActivitySignInBinding;

public class setUserType {

    public String userTypeClinic = "Clinic User";
    public String userTypePatient = "Patient User";
    public String userType = userTypePatient;
    private Boolean switchState;

    public void setUserType() {


    }
    public String checkUserTypeSwitch(Switch simpleSwitch) {
        // initiate a Switch

        // check current state of a Switch (true or false).
        try {
            switchState = simpleSwitch.isChecked();
        }
        catch (Exception e)
        {
            switchState = false;
        }

        if (switchState == true)
        {
            userType = userTypeClinic;
        }
        else
            {
                userType = userTypePatient;
        }



        return userType;
    }
}
