package com.example.project.coursepicker.lib;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Lawrence Ayoub on 3/24/2018.
 */

@IgnoreExtraProperties
public class Day {

    private String dayName;
    private String start;
    private String end;

    protected Day() {

    }

    protected Day(String dayName, String start, String end) {
        this.dayName = dayName;
        this.start = start;
        this.end = end;
    }

    public String getDayName() {
        return dayName;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

}
