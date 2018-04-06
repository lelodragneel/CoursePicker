package com.example.project.coursepicker;

/**
 *
 * @author michael
 * @see
 * All button names directly reflect name in UI and function
 * All text fields directly reflect name in UI and function
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ScheduleActivity extends AppCompatActivity {


    private ListView contactListView;
    private FirebaseListAdapter<Course> firebaseAdapter;
    //declaring local firebase variables TODO: put these in session
    private DatabaseReference firebaseReference;
    private FirebaseDatabase firebaseDBInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Toolbar toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set-up Firebase
        firebaseDBInstance = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDBInstance.getReference("Course");

        Query query = firebaseReference.orderByChild("order");

        //Get the reference to the UI contents
        contactListView = findViewById(R.id.listView);

        //Set up the List View
        firebaseAdapter = new FirebaseListAdapter<Course>(this, Course.class,
                android.R.layout.simple_list_item_1, query) {

            //TODO: sort properly by day and time

            @Override
            protected void populateView(View v, Course model, int position) {
                TextView contactName = v.findViewById(android.R.id.text1);
                contactName.setText(model.getClassDays() + " - " + model.getClassTime() + " - " + model.getName());
            }

            //@Override
        };
        contactListView.setAdapter(firebaseAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is called everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                No action required
                Course person = (Course) firebaseAdapter.getItem(position);
                showDetailView(person);
                */
            }
        });
    }

    public void createContactButton(View v)
    {
        //Intent intent=new Intent(this, CreateBuisnessAcitivity.class);
        //startActivity(intent);
    }

    private void showDetailView(Course person)
    {
        //Intent intent = new Intent(this, DetailViewActivity.class);
        //intent.putExtra("Contact", person);
        //startActivity(intent);
    }

}
