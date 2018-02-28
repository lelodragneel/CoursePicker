package com.example.project.coursepicker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Brianna on 2018-02-27.
 */

public class UserCourses extends AppCompatActivity{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, String> listDataChild;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        expListView = (ExpandableListView) findViewById(R.id.exp);


        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
    /*
     * Build the courses list
     */
    private void prepareListData() {

        String ID = session.getID();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, String>();

        //Global variables (for DB connection)
        AppData appData = (AppData)getApplication();
        Query userCourses;

        //Set up DB connection
        appData.DB = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.DB.getReference("Users");

        userCourses = appData.firebaseReference.orderByChild("Users").equalTo(ID).orderByChild("Courses");

        //set up listener to pull course data from DB
        userCourses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot course : dataSnapshot.getChildren()) {

                    Course courses = course.getValue(Course.class);

                    listDataHeader.add(courses.name);
                    listDataChild.put(listDataHeader.get(i), "Days: " + courses.classDays +
                                                             "\nTime: " + courses.classTime +
                                                             "\nTerm: " + courses.term);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
    }
}
