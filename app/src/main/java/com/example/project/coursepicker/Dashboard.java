package com.example.project.coursepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void profileBtnListener(View view) {

    }

    public void calendarBtnListener(View view) {

    }

    public void coursesBtnListener(View view) {
        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);
    }

    public void settingsBtnListener(View view) {

    }
}
