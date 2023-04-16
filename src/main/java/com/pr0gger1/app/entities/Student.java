package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Human;
import com.pr0gger1.database.DataTables;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Student extends Human {
    private int facultyId = -1;
    private int directionId = -1;
    public short course;
    public float scholarship;
    public int phone;
    public String fullName;

    public Student() {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
    }

    public Student(int directionId, int facultyId) {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
        this.directionId = directionId;
        this.facultyId = facultyId;
    }

    public int getDirectionId() {
        return directionId;
    }

    public void setBirthday(LocalDate date) {
        this.birthday = date;
    }

    public int getFacultyId() {
        return facultyId;
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
