package com.example.project.coursepicker.lib;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lawrence Ayoub on 3/24/2018.
 */

@IgnoreExtraProperties
public class Student {

    private final String studentName;
    private final String studentId;
    private HashMap<String, Boolean> studentCourses = new HashMap<>();
    private String studentEmail;
    private String studentPassword;
    private long studentPhone = 0;

    protected Student() {
        studentName = "";
        studentId = "";
    }

    protected Student(String studentName, String studentId, HashMap<String, Boolean> studentCourses,
                   String studentEmail, String studentPassword, long studentPhone) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentCourses = studentCourses;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
        this.studentPhone = studentPhone;
    }

    public boolean removeCourse(String courseId) {
        if (studentCourses.containsKey(courseId)) {
            studentCourses.remove(courseId);
            return true;
        }
        return false;
    }

    public boolean addCourse(String courseId) {
        if (!studentCourses.containsKey(courseId)) {
            studentCourses.put(courseId, true);
            return true;
        }
        return false;
    }

    public String getStudentName() {
        return studentName;
    }

    public HashMap<String, Boolean> getStudentCourses() {
        return studentCourses;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public long getStudentPhone() {
        return studentPhone;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("studentName", studentName);
        result.put("studentId", studentId);
        result.put("studentCourses", studentCourses);
        result.put("studentEmail", studentEmail);
        result.put("studentPassword", studentPassword);
        result.put("studentPhone", studentPhone);

        return result;
    }

}
