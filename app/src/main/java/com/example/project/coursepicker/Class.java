/**
 * Created by michael on 09/03/18.
 */

package com.example.project.coursepicker;

import java.io.Serializable;

public class Class implements Serializable {

    private String className;
    private String classDays;
    private String classTime;

    protected Class(String className, String classDays, String classTime){
        this.classTime = className;
        this.classDays = classDays;
        this.classTime = classTime;
    }

    public String getClassDays() {
        return classDays;
    }

    public String getClassTime() {
        return classTime;
    }

    public String getClassName() {
        return className;
    }

    public int getClassStartTime() {
        return Integer.parseInt(classTime.split("-",1)[0]);
    }

    public int getClassEndTime() {
        return Integer.parseInt(classTime.split("-",1)[1]);
    }

}