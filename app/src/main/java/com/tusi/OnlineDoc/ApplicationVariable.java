package com.tusi.OnlineDoc;

import android.app.Application;

public class ApplicationVariable extends Application {

    private String UserType;

    public  String getUserTypeVariable() {
        return UserType;
    }

    public  void setUserTypeVariable(String userType) {

        this.UserType = userType;

    }
}
