package com.example.project.coursepicker;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Briana on 2018-02-23.
 * (Following the example posted by Juliano :
 * https://github.com/jmfranz/A4csci3130/
 * */

public class AppData extends Application {

    public DatabaseReference firebaseReference;
    public FirebaseDatabase DB;
}
