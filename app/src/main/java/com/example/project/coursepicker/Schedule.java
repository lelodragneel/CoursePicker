package com.example.project.coursepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Schedule extends AppCompatActivity {

    DatabaseReference db_root;
    List<String> listDataHeader;
    HashMap<String, Class> listDataChild;
    ClassListAdapter listAdapter;
    ExpandableListView expListView;
    Spinner ddTerm;
    ArrayList<Class> fallCourses;
    //ArrayList<Course> winterCourses;

    private String term;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init data structures for accordion
        listDataChild = new HashMap<>();
        listDataHeader = new ArrayList<>();

        ddTerm = findViewById(R.id.spinner);
        expListView = findViewById(R.id.exp);

        // set spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[] {"Fall", "Winter"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddTerm.setAdapter(adapter);

        ddTerm.setSelection(0); // set default selected value

        // listener for when a new term is selected in spinner
        ddTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshAccordion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // declare database root
        db_root = FirebaseDatabase.getInstance().getReference();

        // asynchronous listener to retrieve fall term data
//        DatabaseReference db_fall = db_root.child("Courses").child(username).child("Courses").child(term);
//        //DatabaseReference db_fall = db_root.child("Fall Term ");
//        db_fall.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                // clear previous data
//                fallCourses = new ArrayList<>();
//
//                // loop through every fall course in db
//                for (DataSnapshot pSnapShot : dataSnapshot.getChildren()) {
//
//                    //TODO:Extract different details for relevant courses and times
//                    // extract course details
//                    String name = pSnapShot.getKey();
//                    String classDays = pSnapShot.child("Class Days").getValue(String.class);
//                    String description = pSnapShot.child("Description").getValue(String.class);
//                    String classTime = pSnapShot.child("Class Time").getValue(String.class);
//
//                    // assign course details
//                    //fallCourses.add(new Course(classDays, classTime, description,
//                    //        name, prerequisites, seatsAvail));
//
//
//                    //TODO: Add something here for conflict resolution
//
//                    // refresh accordion
//                    refreshAccordion();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });

        DatabaseReference db_fall = db_root.child("Users").child("jg123456"/*username*/).child("Courses").child("Fall"/*term*/);
        db_fall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear previous data
                fallCourses = new ArrayList<>();
                //loop through every fall course in db

                //TODO:Actually wire this into firebase
                /*for (DataSnapshot pSnapShot : dataSnapshot.getChildren()) {
                    String name = pSnapShot.getKey();
                    String classDays = pSnapShot.child("Class Days").getValue(String.class);
                    String classTime = pSnapShot.child("Class Time").getValue(String.class);
                }*/

                //TODO:Sort array list to display class in proper order
                fallCourses.add(new Class("Fine Wine", "R", "0200-0500" ));
                fallCourses.add(new Class("Fine Wine", "R", "0200-0500" ));
                fallCourses.add(new Class("Fine Wine", "R", "0200-0500" ));
                fallCourses.add(new Class("Fine Wine", "R", "0200-0500" ));
                //TODO:highlight conflicts
            }
        @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


    }
    /*
     * Checks to see which term is selected in the spinner, then returns a database reference
     * pointing to the correct sub-database
     */

    private void refreshAccordion() {

        String selected = ddTerm.getSelectedItem().toString();

        // clear previous data
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        if (fallCourses != null) {
            for (Class i : fallCourses) {
                listDataHeader.add(i.getClassName());
                listDataChild.put(i.getClassName(), i);
            }
        } else {
            Log.e("refreshAccordion", "could not populate accordion");
        }

        // recreate adapter with new data
        listAdapter = new ClassListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        listAdapter.notifyDataSetChanged(); // sync accordion data

    }

}