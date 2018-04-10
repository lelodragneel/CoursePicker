package com.example.project.coursepicker.lib;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lawrence Ayoub on 3/23/2018.
 */

public class FireHelper {

    private static final FireHelper ourInstance = new FireHelper();
    private HashMap<String, Course> globalCourses = new HashMap<>();
    private HashMap<String, Student> globalStudents  = new HashMap<>();
    private final DatabaseReference db_courses = FirebaseDatabase.getInstance().getReference()
            .child("Courses-Beta");
    private final DatabaseReference db_students = FirebaseDatabase.getInstance().getReference()
            .child("Students-Beta");

    public static FireHelper getInstance() {
        return ourInstance;
    }

    private FireHelper() {

        db_courses.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!globalCourses.containsKey(dataSnapshot.getKey())) {
                    globalCourses.put(dataSnapshot.getKey(), dataSnapshot.getValue(Course.class));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (globalCourses.containsKey(dataSnapshot.getKey())) {
                    globalCourses.remove(dataSnapshot.getKey());
                    globalCourses.put(dataSnapshot.getKey(), dataSnapshot.getValue(Course.class));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (globalCourses.containsKey(dataSnapshot.getKey())) {
                    globalCourses.remove(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        db_students.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!globalStudents.containsKey(dataSnapshot.getKey())) {
                    globalStudents.put(dataSnapshot.getKey(), dataSnapshot.getValue(Student.class));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (globalStudents.containsKey(dataSnapshot.getKey())) {
                    globalStudents.remove(dataSnapshot.getKey());
                    globalStudents.put(dataSnapshot.getKey(), dataSnapshot.getValue(Student.class));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (globalStudents.containsKey(dataSnapshot.getKey())) {
                    globalStudents.remove(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }

    public Collection<Course> getCourses() {
        return globalCourses.values();
    }

    public Collection<Course> getCourses(String term) {
        Collection<Course> c = new ArrayList<>();
        for (Course i : globalCourses.values()) {
            if (i.getCourseTerm().equalsIgnoreCase(term)) {
                c.add(i);
            }
        }
        return c;
    }

    public Course getCourse(String courseId) {
        if (globalCourses.containsKey(courseId)) {
            return globalCourses.get(courseId);
        } else {
            Log.e(getClass().getName(), String.format("invalid course id %s", courseId));
        }
        return null;
    }

    public Course getCourseByName(String courseName) {
        for (Course i : globalCourses.values()) {
            if (i.getCourseName().equalsIgnoreCase(courseName)) {
                return i;
            }
        }
        Log.e(getClass().getName(), String.format("found no courses with name %s", courseName));
        return null;
    }

    public Collection<Student> getStudents() {
        return globalStudents.values();
    }

    public Student getStudent(String studentId) {
        if (globalStudents.containsKey(studentId)) {
            return globalStudents.get(studentId);
        } else {
            Log.e(getClass().getName(), String.format("invalid student id %s", studentId));
        }
        return null;
    }

    public Collection<Course> getStudentCourses(String studentId) {
        Collection<Course> c = new ArrayList<>();
        if (globalStudents.containsKey(studentId)) {
            for (String courseId : globalStudents.get(studentId).getStudentCourses().keySet()) {
                if (globalCourses.containsKey(courseId)) {
                    c.add(globalCourses.get(courseId));
                }
            }
        } else {
            Log.e(getClass().getName(), String.format("invalid student id %s", studentId));
        }
        return c;
    }

    public void updateStudent(Student student) {
        if (globalStudents.containsKey(student.getStudentId())) {
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(student.getStudentId(), student);
            db_students.updateChildren(childUpdates);
        } else {
            Log.e(getClass().getName(), String.format("invalid student id %s", student.getStudentId()));
        }
    }

    public boolean addCourseToStudent(String studentId, String courseId) {
        if (globalStudents.containsKey(studentId)) {
            if (globalCourses.containsKey(courseId)) {
                if (!globalStudents.get(studentId).getStudentCourses().containsKey(courseId)) {
                    if (performSeatArithmetic(courseId, -1)) {
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(courseId, true);
                        db_students.child(studentId).child("studentCourses").updateChildren(childUpdates);
                        return true;
                    }
                } else {
                    Log.e(getClass().getName(),
                            String.format("student %s already registered in %s", studentId, courseId));
                }
            } else {
                Log.e(getClass().getName(), String.format("invalid course id %s", courseId));
            }
        } else {
            Log.e(getClass().getName(), String.format("invalid student id %s", studentId));
        }
        return false;
    }

    public boolean removeCourseFromStudent(String studentId, String courseId) {
        if (globalStudents.containsKey(studentId)) {
            if (globalCourses.containsKey(courseId)) {
                if (globalStudents.get(studentId).getStudentCourses().containsKey(courseId)) {
                    if (performSeatArithmetic(courseId, 1)) {
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(courseId, true);
                        db_students.child(studentId).child("studentCourses").child(courseId).removeValue();
                        return true;
                    }
                } else {
                    Log.e(getClass().getName(),
                            String.format("student %s not registered in %s", studentId, courseId));
                }
            } else {
                Log.e(getClass().getName(), String.format("invalid course id %s", courseId));
            }
        } else {
            Log.e(getClass().getName(), String.format("invalid student id %s", studentId));
        }
        return false;
    }

    public boolean performSeatArithmetic(String courseId, int num) {
        if (globalCourses.containsKey(courseId)) {
            if ((getCourse(courseId).getCourseSeatsRemaining() + num) >= 0) {
                int c = getCourse(courseId).getCourseSeatsRemaining() + num;
                db_courses.child(courseId).child("courseSeatsRemaining").setValue(c);
                return true;
            } else {
                Log.e(getClass().getName(), String.format("insufficient seats for %s", courseId));
            }
        } else {
            Log.e(getClass().getName(), String.format("invalid course id %s", courseId));
        }
        return false;
    }

}
