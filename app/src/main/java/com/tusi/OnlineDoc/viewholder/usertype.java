package com.tusi.OnlineDoc.viewholder;

import android.app.Activity;
import android.content.SharedPreferences;

public class usertype {
    private String User;
    Activity ak;
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;



    public usertype(Activity Ak) {
        ak = Ak;
        mPrefs = ak.getSharedPreferences("label", 0);
        mEditor = mPrefs.edit();
    }

    public usertype(String user_) {
        this.User = user_;

    }

    public void setUser(String user) {
        this.User = user;
        mEditor.putString("tag", this.User).commit();
    }

    public String getUser() {
        User = mPrefs.getString("tag", "anonymous");
        return User;
    }


}
