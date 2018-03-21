package com.example.project.coursepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Profile touch listener
        LinearLayout l1 = findViewById(R.id.linearLayout);
        l1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setActivated(true);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setActivated(false);
                    return true;
                }
                return false;
            }
        });

        // Schedule touch listener
        LinearLayout l2 = findViewById(R.id.linearLayout2);
        l2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setActivated(true);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setActivated(false);
                    Intent intent = new Intent(v.getContext(), Schedule.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // Courses touch listener
        LinearLayout l3 = findViewById(R.id.linearLayout3);
        l3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setActivated(true);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setActivated(false);
                    Intent intent = new Intent(v.getContext(), CoursesActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // Settings touch listener
        LinearLayout l4 = findViewById(R.id.linearLayout4);
        l4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setActivated(true);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setActivated(false);
                    return true;
                }
                return false;
            }
        });

        // logout touch listener
        LinearLayout l6 = findViewById(R.id.linearLayout6);
        l6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setActivated(true);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setActivated(false);
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

}
