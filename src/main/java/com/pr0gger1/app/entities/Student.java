package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Human;
import com.pr0gger1.database.DataTables;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Student extends Human {
    private int studentId;
    private int facultyId;
    private int directionId;
    private short course;
    private float scholarship;
    private long phone;
    private String fullName;

    public Student() {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
    }

    public Student(int directionId, int facultyId) {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
        this.directionId = directionId;
        this.facultyId = facultyId;
    }

    public int getStudentId() {
        return studentId;
    }
    public int getDirectionId() {
        return directionId;
    }
    public int getFacultyId() {
        return facultyId;
    }

    public float getScholarship() {
        return scholarship;
    }

    public long getPhone() {
        return phone;
    }

    public short getCourse() {
        return course;
    }

    public String getFullName() {
        return fullName;
    }


    public void setBirthday(LocalDate date) {
        this.birthday = date;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourse(short course) {
        this.course = course;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setScholarship(float scholarship) {
        this.scholarship = scholarship;
    }
    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return String.format(
            "Student:{\n" +
            "\tfacultyId: %d," +
            "\n\tdirectionId: %d," +
            "\n\tfull_name: %s," +
            "\n\tcourse: %d," +
            "\n\tbirthday: %s," +
            "\n\tscholarship: %f," +
            "\n\tphone: %d," +
            "\n}",
            facultyId, directionId,
            fullName, course,
            birthday != null ? birthday.toString() : "null",
            scholarship, phone
        );
    }

}
