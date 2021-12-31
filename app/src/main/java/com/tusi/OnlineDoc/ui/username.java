package com.tusi.OnlineDoc.ui;

import android.app.Activity;
import android.content.SharedPreferences;

public class username {
    private String User;
    Activity ak;
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;



    public username(Activity Ak) {
        ak = Ak;
        mPrefs = ak.getSharedPreferences("names", 0);
        mEditor = mPrefs.edit();
    }

    public username(String user_) {
        this.User = user_;

    }

    public void setUserName(String user) {
        this.User = user;
        mEditor.putString("name", this.User).commit();
    }

    public String getUserName() {
        User = mPrefs.getString("name", "anonymous");
        return User;
    }


}
