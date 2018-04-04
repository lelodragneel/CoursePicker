package com.example.project.coursepicker.lib;

import com.google.firebase.database.IgnoreExtraProperties;

import java.time.LocalTime;

/**
 * Created by Lawrence Ayoub on 3/24/2018.
 */

@IgnoreExtraProperties
public class Day {

    private String dayName;
    private LocalTime start;
    private LocalTime end;

    protected Day() {

    }

    protected Day(String dayName, String start, String end) {
        this.dayName = dayName;
        this.start = LocalTime.of(Integer.parseInt(start.substring(0, 1)),
                Integer.parseInt(start.substring(2, 3)));
        this.end = LocalTime.of(Integer.parseInt(end.substring(0, 1)),
                Integer.parseInt(end.substring(2, 3)));
    }

    public String getDayName() {
        return dayName;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

}
