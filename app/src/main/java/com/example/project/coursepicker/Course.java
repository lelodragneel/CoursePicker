package com.example.project.coursepicker;

import java.io.Serializable;

public class Course implements Serializable {

    public String classDays;
    public String classTime;
    public String description;
    public String name;
    public String prerequisites;
    public int seatsAvail;

    public Course(String classDays, String classTime, String description, String name,
                  String prerequisites, int seatsAvail){
        this.classDays = classDays;
        this.classTime = classTime;
        this.description = description;
        this.name = name;
        this.prerequisites = prerequisites;
        this.seatsAvail = seatsAvail;
    }
}