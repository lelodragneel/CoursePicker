package com.example.project.coursepicker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, String> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        expListView = (ExpandableListView) findViewById(R.id.exp);

        // build courses list
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Build the courses list
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, String>();

        // Get all course names from db
        listDataHeader.add("Course1");
        // Add course preview details
        String coursePreviewDesc = "this is small description of course 1 showing in the accordion";
        listDataChild.put(listDataHeader.get(0), coursePreviewDesc);

        listDataHeader.add("Course2");
        // Add course preview details
        coursePreviewDesc = "this is small description of course 2 showing in the accordion";
        listDataChild.put(listDataHeader.get(1), coursePreviewDesc);

        listDataHeader.add("Course3");
        // Add course preview details
        coursePreviewDesc = "this is small description of course 3 showing in the accordion";
        listDataChild.put(listDataHeader.get(2), coursePreviewDesc);

    }

}
