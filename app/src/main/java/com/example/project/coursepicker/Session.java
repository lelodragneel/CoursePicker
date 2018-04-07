package com.example.project.coursepicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Application;

import java.util.List;

/**
 *  Created by Brianna on 2018-02-27.
 *  Session variable for keeping track
 *  of user that's logged in.
 */

public class Session extends Application {

    private SharedPreferences session;
    private SharedPreferences.Editor edit;
    String loginID;

    public Session(Context context) {
        session = PreferenceManager.getDefaultSharedPreferences(context);
        edit = session.edit();
    }

    public Session()
    {}

    public void setID(String loginID)
    {
        edit.putString("ID", loginID);
        edit.apply();
    }

    public String getID()
    {
        loginID = session.getString("ID", "");
        return loginID;
    }
}
