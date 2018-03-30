package com.example.project.coursepicker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lawrence, Brianna, Kenny, Jake
 * Activity class for displaying and manipulating
 * the course selection.
 * Courses are organized by semester (fall/winter).
 */
public class CoursesActivity extends AppCompatActivity {

    private DatabaseReference db_root;
    private List<String> listDataHeader;
    private HashMap<String, Course> listDataChild;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private Spinner ddTerm;
    private ArrayList<Course> fallCourses;
    private ArrayList<Course> winterCourses;
    private String uid = "Ab123456"; // mockup user id variable
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

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
                android.R.layout.simple_spinner_item, new String[]{"Fall", "Winter"});
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
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // declare database root
        db_root = FirebaseDatabase.getInstance().getReference();

        // asynchronous listener to retrieve fall term data
        DatabaseReference db_fall = db_root.child("Fall Term ");
        db_fall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // clear previous data
                fallCourses = new ArrayList<>();

                // loop through every fall course in db
                for (DataSnapshot pSnapShot : dataSnapshot.getChildren()) {

                    // extract course details
                    String name = pSnapShot.getKey();
                    String classDays = pSnapShot.child("Class Days").getValue(String.class);
                    String description = pSnapShot.child("Description").getValue(String.class);
                    String classTime = pSnapShot.child("Class Time").getValue(String.class);
                    String prerequisites = pSnapShot.child("Prerequisites").getValue(String.class);
                    int seatsAvail = pSnapShot.child("Seats Available").getValue(int.class);

                    // assign course details
                    fallCourses.add(new Course(classDays, classTime, description,
                            name, prerequisites, seatsAvail));

                    // refresh accordion
                    refreshAccordion();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // asynchronous listener to retrieve winter term data
        DatabaseReference db_winter = db_root.child("Winter Term");
        db_winter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // clear previous data
                winterCourses = new ArrayList<>();

                // loop through every winter course in db
                for (DataSnapshot pSnapShot : dataSnapshot.getChildren()) {

                    // extract course details
                    String name = pSnapShot.getKey();
                    String classDays = pSnapShot.child("Class Days").getValue(String.class);
                    String description = pSnapShot.child("Description").getValue(String.class);
                    String classTime = pSnapShot.child("Class Time").getValue(String.class);
                    String prerequisites = pSnapShot.child("Prerequisites").getValue(String.class);
                    int seatsAvail = pSnapShot.child("Seats Available").getValue(int.class);

                    // assign course details
                    winterCourses.add(new Course(classDays, classTime, description,
                            name, prerequisites, seatsAvail));

                    // refresh accordion
                    refreshAccordion();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * addCourse method used to add and drop a course
     * from a user's current schedule
     *
     * @param view The view where the add/drop button was clicked
     */
    public void addCourse(View view) {
        String selected = ddTerm.getSelectedItem().toString();
        final String course = view.getTag().toString();

        switch (selected) {
            case "Fall":
                db_root.child("Users").child(uid).child("Courses").child("Fall")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot data) {
                                if (!data.hasChild(course)) {

                                    seatCounterAddition(db_root.child("Fall Term ").child(course), -1, course, "Fall");
                                } else {
                                    displayAlert(course, "Fall", "Are you sure you want to drop ", "Drop Course");

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                break;

            case "Winter":
                db_root.child("Users").child(uid).child("Courses").child("Winter")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot data) {
                                if (!data.hasChild(course)) {
                                    seatCounterAddition(db_root.child("Winter Term").child(course), -1, course, "Winter");
                                }
                                else
                                    displayAlert(course, "Winter", "Are you sure you want to drop ", "Drop Course");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                break;
        }

        refreshAccordion();
    }

    private void seatCounterAddition(final DatabaseReference courseSubDatabase, final int add, final String course, final String semester) {
        courseSubDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = dataSnapshot.child("Seats Available").getValue(Integer.class);
                if (counter > 0) {
                    db_root.child("Users").child(uid).child("Courses").child("Winter")
                            .child(course).setValue(true);
                    Toast.makeText(getApplicationContext(), "Successfully " +
                            "Enrolled in " + course, Toast.LENGTH_LONG).show();

                    counter += add;
                    courseSubDatabase.child("Seats Available").setValue(counter);
                }
                else displayAlert(course, semester, "No seats available. Request override for ", "Course Full");




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * displayAlert method is called when the user clicks the drop button
     * on a course they're already enrolled in. This method will display a
     * pop-up allowing the option of dropping the course, or cancelling the drop request.
     * @param course    the course the user is attempting to drop
     * @param semester  the semester in which the course is held (Fall or Winter)
     */
    private void displayAlert(final String course, final String semester, final String message, final String title) {

        //create new alert dialog windows
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);   //allow it to be cancelled

        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message + course + "?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (title.equals("Drop Course")) {
                    db_root.child("Users").child(uid).child("Courses").child(semester).child(course).removeValue();
                    Toast.makeText(getApplicationContext(), "Successfully Dropped " + course,
                            Toast.LENGTH_LONG).show();
                    seatCounterAddition(db_root.child(semester).child(course), 1, course, semester);
                }
                else if (title.equals("Course Full")) {
                    db_root.child("Users").child(uid).child("Requests").child(semester).child("overrideFull").child(course).setValue(true);
                    Toast.makeText(getApplicationContext(), "Override Requested for " + course,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertBuilder.show();
    }

    /**
     * Method specifically for incrementing and decrementing the seat counter for a specified course
     *
     * @param courseSubDatabase     The database reference with the root of a course
     * @param add                   Integer to perform arithmetic with seat counter, can be negative
     */
  /*  private void seatCounterAddition(final DatabaseReference courseSubDatabase, final int add) {
        courseSubDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = dataSnapshot.child("Seats Available").getValue(Integer.class);
                counter += add;
                courseSubDatabase.child("Seats Available").setValue(counter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // update accordion to display new seat count
        refreshAccordion();
    } */


    /**
     * Method to check to see which term is selected in the spinner, then returns a database reference
     * pointing to the correct sub-database
     */
    private void refreshAccordion() {

        String selected = ddTerm.getSelectedItem().toString();

        // clear previous data
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        switch (selected) {
            case "Fall":
                if (fallCourses != null) {
                    for (Course i : fallCourses) {
                        listDataHeader.add(i.getName());
                        listDataChild.put(i.getName(), i);
                    }
                }
                break;
            case "Winter":
                if (winterCourses != null) {
                    for (Course i : winterCourses) {
                        listDataHeader.add(i.getName());
                        listDataChild.put(i.getName(), i);
                    }
                }
                break;
            default:
                Log.e("refreshAccordion","could not populate accordion");
        }

        // recreate adapter with new data
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        listAdapter.notifyDataSetChanged(); // sync accordion data

    }

}