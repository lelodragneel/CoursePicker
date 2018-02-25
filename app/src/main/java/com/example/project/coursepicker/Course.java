package com.example.project.coursepicker;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Course implements Serializable {

    public String classDays;
    public String classTime;
    public String description;
    public String name;
    public String prerequisites;
    public int seatsAvail;
    public String term;

    public Course() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Course(String classDays, String classTime, String description,
                  String name, String prerequisites, int seatsAvail,
                  String term){
        this.classDays = classDays;
        this.classTime = classTime;
        this.description = description;
        this.name = name;
        this.prerequisites = prerequisites;
        this.seatsAvail = seatsAvail;
        this.term = term;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("classDays", classDays);
        result.put("classTime", classTime);
        result.put("description", description);
        result.put("name", name);
        result.put("prerequisites", prerequisites);
        result.put("seatsAvail", seatsAvail);
        result.put("term", term);

        return result;
    }
}