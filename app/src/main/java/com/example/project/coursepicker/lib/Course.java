package com.example.project.coursepicker.lib;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Course implements Serializable {

    private String courseDescription;
    private String courseId;
    private String courseName;
    private HashMap<String, Day> coursePeriods = new HashMap<>();
    private HashMap<String, Boolean> coursePrerequisites = new HashMap<>();
    private int courseSeatsRemaining = 0;
    private String courseTerm;

    protected Course() {

    }

    protected Course(String courseDescription, String courseId, String courseName,
                     HashMap<String, Day> coursePeriods, HashMap<String, Boolean> coursePrerequisites,
                     int courseSeatsRemaining, String courseTerm){
        this.courseDescription = courseDescription;
        this.courseId = courseId;
        this.courseName = courseName;
        this.coursePeriods = coursePeriods;
        this.coursePrerequisites = coursePrerequisites;
        this.courseSeatsRemaining = courseSeatsRemaining;
        this.courseTerm = courseTerm;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public HashMap<String, Day> getCoursePeriods() {
        return coursePeriods;
    }

    public HashMap<String, Boolean> getCoursePrerequisites() {
        return coursePrerequisites;
    }

    public int getCourseSeatsRemaining() {
        return courseSeatsRemaining;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

}